package com.nyse.redpanda;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import pulse.BQTCloudStreaming.BQTMessage; 

public class NYSE_BQTMessage_Consumer {
    public static void main(String[] args) {

        // Kafka Consumer Configuration
        Properties properties = new Properties();
        //update bootstrap servers. See ReadMe for details.
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "");
        
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, ""); //update group_id # {name_of_topic_[your_AWS_Account_Nmbr]} Example: bqt_trd_str_1_1234567890
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        
        // SASL configuration
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "SCRAM-SHA-256");

        //update username and password to values provided by NYSE.
        String username = "";
        String password="";
        properties.put("sasl.jaas.config", String.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",username, password));
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(properties);
        String topic_name = "bqt_trd_str_1";
        consumer.subscribe(Arrays.asList(topic_name));        
        System.out.println("Consuming messages from topic: " + topic_name);        
        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(1000));                
                System.out.println("Number of records polled: " + records.count());
                for (ConsumerRecord<String, byte[]> record : records) {
                    try {
                        System.out.println("inside topic " + record.topic());
                        // Parse the Protobuf message
                        BQTMessage message = BQTMessage.parseFrom(record.value());
                        // Print details of the message
                        System.out.println("Received BQTMessage:" + message.toString());
                    } catch (Exception e) {
                        System.err.println("Failed to deserialize Protobuf message: " + e.getMessage());
                    }
                }
            }
        } finally {
            consumer.close();
        }
    }
}