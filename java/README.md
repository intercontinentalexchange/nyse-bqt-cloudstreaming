# NYSE BQTMessage Consumer Test
This README file provides instructions and details for the NYSE_BQTMessage_Consumer.java script, which is designed to validate connectivity to the NYSE BQT Kafka offering.

# Overview
The NYSE_BQTMessage_Consumer.java script initializes a Kafka consumer, subscribes to the NYSE BQT Kafka topics, and prints messages.

# Prerequisites
Before running the test script, ensure you have the following prerequisites:

   Java: Version 17
   Gradle: Version 8.0 or higher
   Kafka: Version 3.5.1
   Protobuf: Version 3.24.3
   Ensure you have the latest proto file from https://www.nyse.com/publicdocs/nyse/data/BQT_Cloud_Streaming.proto downloaded in proto folder.

Note: We have 2 environments for this offering viz. Load Test and Prod. Please see respective cloud onboarding documentation below:

| Environment                    | Documentation Link                                                                        |
|--------------------------------|-------------------------------------------------------------------------------------------|
| NYSE Kafka Cluster Load Test   | https://www.nyse.com/publicdocs/nyse/data/NYSE_Kafka_Cluster_Load_Test_Environment.pdf    |
| NYSE Kafka Cluster Production  | https://www.nyse.com/publicdocs/nyse/data/NYSE_Kafka_Cluster_Production_Environment.pdf   |

# To run the NYSE_BQTMessage_Consumer.java script, follow these steps:
Configure the Script: Open the NYSE_BQTMessage_Consumer.java script and configure the following parameters:

1. topic_name: The name of the Kafka topic to consume messages.
2. BOOTSTRAP_SERVERS_CONFIG: Select the appropriate bootstrap servers based on the AWS region and environment:

    | Bootstrap Servers                                      | Description                    |
    |--------------------------------------------------------|--------------------------------|
    | `['broker.lt.use1.bqt.pulse.nyse:9094']`               | Load Test us-east-1 endpoint   |
    | `['broker.prod.use1.bqt.pulse.nyse:9094']`             | Production us-east-1 endpoint  |

3. GROUP_ID_CONFIG: Update GROUP_ID_CONFIG to pull data from Kafka. GROUP_ID_CONFIG should be 
     {name_of_topic_[your_AWS_Account_Nmbr]}. Example: bqt_trd_str_1_1234567890
4. username: Kafka username. Use the username shared by NYSE with you.
5. password: Password. Use the password shared by NYSE with you.

# Build the Project
gradle clean build

# Run the Consumer
gradle run

# Troubleshooting
If you encounter any issues, ensure that:

1. The topic name is correct.
2. The username, password, and group_id are correct.
