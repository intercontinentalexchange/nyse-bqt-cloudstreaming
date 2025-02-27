import threading
import BQT_Cloud_Streaming_pb2 as BQT_Cloud_Streaming_pb  # Generated from your proto file using protoc # check the geenrated file name and update if required
from kafka import KafkaConsumer

class Consumer(threading.Thread):
    """
    A Kafka consumer thread that continuously polls messages from a Kafka topic
    """

    def __init__(self, topic_name='your_topic_name'):
        """
        Initializes the Consumer object.

        Args:
            topic_name (str): The name of the Kafka topic to consume messages from. Default is 'your_topic_name'.
            
        """
        super().__init__()
        self.topic_name = topic_name        
        self.consumer = KafkaConsumer(
            self.topic_name,
            bootstrap_servers=['broker.lt.use1.bqt.pulse.nyse:9094'], #LT us-east-1 endpoint
            #bootstrap_servers=['broker.prod.use1.bqt.pulse.nyse:9094'], #PRD us-east-1 endpoint
            group_id='', # update group_id # {name_of_topic_[your_AWS_Account_Nmbr]} Example: bqt_trd_str_1_1234567890
            security_protocol='SASL_PLAINTEXT',
            sasl_plain_username='', # add username
            sasl_plain_password='', # add password
            sasl_mechanism='SCRAM-SHA-256',
            auto_offset_reset='latest',
            value_deserializer=lambda x: BQT_Cloud_Streaming_pb.BQTMessage.FromString(x),
        )
        self.consumer.subscribe([self.topic_name])
        self.stop_event = threading.Event()

    def stop(self):
        """
        Stops the consumer by setting the stop event.
        """
        self.stop_event.set()

    def run(self):
        """
        Runs the consumer thread, continuously polling messages from the Kafka topic and calculating the latency.
        """
        try:
            while not self.stop_event.is_set():
                for message in self.consumer:
                    print(message)

                    if self.stop_event.is_set():
                        break

        except KeyboardInterrupt:
            print("Interrupted by user")
        finally:
            self.consumer.close()

# topic_name needs to be updated if not using bqt_trd_str_1
if __name__ == "__main__":
    consumer_thread = Consumer(topic_name='bqt_trd_str_1')
    print(consumer_thread.topic_name)
    consumer_thread.start()
    consumer_thread.join()