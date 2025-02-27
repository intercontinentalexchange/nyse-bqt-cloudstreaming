
# NYSE BQTMessage Consumer Test

This README file provides instructions and details for the `NYSE_BQTMessage_Consumer.py` script, which is designed to validate connectivity to the NYSE Kafka topic.

## Overview

The `NYSE_BQTMessage_Consumer.py` script is to test the NYSE Kafka connectivity. It initializes a Kafka consumer, subscribes to the NYSE BQT Kafka topics, and prints messages.

## Prerequisites

Before running the test script, ensure you have the following prerequisites:

1. **Python 3.9.x and higher**: Make sure you have Python 3.9.x or higher installed on your system.
2. **Required Libraries**: Install the libraries using pip.
   ```sh
   pip install -r requirements.txt
3. **Protocol Buffers Compiler (protoc)**: Ensure you have the Protocol Buffers compiler installed. This code has used Protoc - 29.1 version.
    The protoc compiler can be downloaded from the Protocol Buffers GitHub releases page. 

Note: Make sure that you follow the steps to configure the necessary connections in your AWS account to establish connectivity to the NYSE BQT Kafka offering.

| Environment                    | Documentation Link                                                                        |
|--------------------------------|-------------------------------------------------------------------------------------------|
| NYSE Kafka Cluster Load Test   | https://www.nyse.com/publicdocs/nyse/data/NYSE_Kafka_Cluster_Load_Test_Environment.pdf    |
| NYSE Kafka Cluster Production  | https://www.nyse.com/publicdocs/nyse/data/NYSE_Kafka_Cluster_Production_Environment.pdf   |

## How to generate BQT_Cloud_Streaming_pb2.py using the protoc compiler

BQT_Cloud_Streaming_pb2.py already exists in the git repository.
To generate the BQT_Cloud_Streaming_pb2.py file from the BQT_Cloud_Streaming.proto file, follow these steps:

1. **Ensure protoc is installed **: Verify that protoc is installed and accessible from your command line.

2. **Download the NYSE proto file https://www.nyse.com/publicdocs/nyse/data/BQT_Cloud_Streaming.proto**
   **Navigate to the directory containing the .proto file**: Open a terminal and navigate to the directory where your BQT_Cloud_Streaming.proto file is located.

3. **Run the protoc command to generate the BQT_Cloud_Streaming_pb2.py file.**
     ```sh
   protoc --python_out=. BQT_Cloud_Streaming.proto
   Note : The above command generates a BQT_Cloud_Streaming_pb2.py file.

## To run the NYSE_BQTMessage_Consumer.py script, follow these steps:

1. **Configure the Script: Open the 'NYSE_BQTMessage_Consumer.py' script and configure the following parameters:**

    **TOPIC_NAME**: The name of the Kafka topic to consume messages.  
    **bootstrap_servers**: Select the appropriate  bootstrap_servers based on the AWS region and environment

    | Bootstrap Servers                                      | Description                    |
    |--------------------------------------------------------|--------------------------------|
    | `['broker.lt.use1.bqt.pulse.nyse:9094']`               | Load Test us-east-1 endpoint   |
    | `['broker.lt.ape1.bqt.pulse.nyse:9093']`               | Load Test ap-east-1 endpoint   |
    | `['broker.prod.use1.bqt.pulse.nyse:9094']`             | Production us-east-1 endpoint  |
    | `['broker.prod.ape1.bqt.pulse.nyse:9093']`             | Production ap-east-1 endpoint  |

    **group_id**: Update group_id to pull data from Kafka. group_id should be {name_of_topic_[your_AWS_Account_Nmbr]} Example: bqt_trd_str_1_1234567890  
    **sasl_plain_username**: Kafka username  
    **sasl_plain_password** Password  

    Run the Script: Execute the script using Python.
     ```sh
    python 'NYSE_BQTMessage_Consumer.py'

# Troubleshooting
If you encounter any issues, ensure that:

- The topic name is correct.
- Validate - username, password and group_id are correct.
- The kafka_python_ng library is installed.


