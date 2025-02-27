
# NYSE BQTMessage Consumer

This README file provides instructions and details for the `NYSE_BQTMessage_Consumer.js` script, which is designed to validate connectivity to the NYSE BQT Kafka offering.

## Overview

The `NYSE_BQTMessage_Consumer.js` script initializes a Kafka consumer, subscribes to the NYSE BQT Kafka topics, and prints messages.

## Prerequisites

Before running the test script, ensure you have the following prerequisites:

1. **Node v18.** : Make sure you have Node v18.* installed on your system.
2. **Required Libraries**: Install the Required Libraries using npm from package.json
   ```sh
   npm install 
3. **Protocol Buffers Compiler (protoc)**: Ensure you have the Protocol Buffers compiler installed. 
    The protoc compiler can be downloaded from the Protocol Buffers GitHub releases page. 

Note: We have 2 environments for this offering viz. Load Test and Production. Please see respective cloud onboarding documentation below:

| Environment                    | Documentation Link                                                                        |
|--------------------------------|-------------------------------------------------------------------------------------------|
| NYSE Kafka Cluster Load Test   | https://www.nyse.com/publicdocs/nyse/data/NYSE_Kafka_Cluster_Load_Test_Environment.pdf    |
| NYSE Kafka Cluster Production  | https://www.nyse.com/publicdocs/nyse/data/NYSE_Kafka_Cluster_Production_Environment.pdf   |

## How to generate BQT_Cloud_Streaming_pb.js using the protoc compiler

BQT_Cloud_Streaming_pb.js already exists in the git repository. 
Optional - Below steps are required to generate the BQT_Cloud_Streaming_pb.js file from the BQT_Cloud_Streaming.proto file. Follow these steps:

1. **Ensure protoc is installed **: Verify that protoc is installed and accessible from your command line.

2. **Download the NYSE proto file https://www.nyse.com/publicdocs/nyse/data/BQT_Cloud_Streaming.proto**
   **Navigate to the directory containing the .proto file**: Open a terminal and navigate to the directory where your BQT_Cloud_Streaming.proto file is located.

3. **Run the protoc command to generate the BQT_Cloud_Streaming_pb.js file.**
     ```sh
   protoc --js_out=import_style=commonjs,binary:. BQT_Cloud_Streaming.proto
   Note : the above command generates a BQT_Cloud_Streaming_pb.js file.

## To run the NYSE_BQTMessage_Consumer.js script, follow these steps:

1. **Configure the Script: Open the 'NYSE_BQTMessage_Consumer.js' script and configure the following parameters:**

    **topic**: The name of the Kafka topic to consume messages.
    **brokers**: Select the appropriate  brokers based on the AWS region and environment

    | Bbrokers                                               | Description                    |
    |--------------------------------------------------------|--------------------------------|
    | `['broker.lt.use1.bqt.pulse.nyse:9094']`               | Load Test us-east-1 endpoint   |
    | `['broker.prod.use1.bqt.pulse.nyse:9094']`             | Production us-east-1 endpoint  |
  
    **groupId**: Update groupId to pull data from Kafka. groupId should be {name_of_topic_[your_AWS_Account_Nmbr]} Example: bqt_trd_str_1_1234567890    
    username: Kafka username  
    password: Kafka Password     

    Run the Script: Execute the script using Node.
     ```sh
    node 'NYSE_BQTMessage_Consumer.js'

# Troubleshooting
If you encounter any issues, ensure that:

- The topic name is correct.
- Validate that username, password and groupId are correct.
- The required libraries are installed.

