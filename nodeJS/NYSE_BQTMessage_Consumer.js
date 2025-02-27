const bqt = require('./bqt_cloud_streaming_pb');
const { Kafka, logLevel } = require('kafkajs')

const kafka = new Kafka({
    connectionTimeout: 120000,
    logLevel: logLevel.INFO,
    requestTimeout: 120000,
    brokers: ['broker.lt.use1.bqt.pulse.nyse:9094'], //LT us-east-1 endpoint 
    //brokers: ['broker.lt.ape1.bqt.pulse.nyse:9093'], //LT ap-east-1 endpoint 
    //brokers: ['broker.prod.use1.bqt.pulse.nyse:9094'], //PRD us-east-1 endpoint
    //brokers: ['broker.prod.ape1.bqt.pulse.nyse:9093'], //PRD ap-east-1 endpoint    
    clientId: '', //# update clientId # {name_of_topic_[your_AWS_Account_Nmbr]} Example: bqt_trd_str_1_1234567890
        sasl: {
    mechanism: 'scram-sha-256', 
            username: '',
            password: ''
  },
})

const topic = 'bqt_trd_str_1'
const consumer = kafka.consumer({ partition:1, groupId: '' }) //# update group_id # {name_of_topic_[your_AWS_Account_Nmbr]} Example: bqt_trd_str_1_1234567890

const run = async () => {
    await consumer.connect()
    await consumer.subscribe({ topic, fromBeginning: true })
    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {        
        try {
            console.log("Sample Proto BQT Message for topic:"+topic);            
            console.log(bqt.BQTMessage.deserializeBinary(message.value));            
        }catch(e){
            console.log(e);
        }
},
})
}
run().catch(e => console.error(`[example/consumer] ${e.message}`, e))

const errorTypes = ['unhandledRejection', 'uncaughtException']
const signalTraps = ['SIGTERM', 'SIGINT', 'SIGUSR2']

errorTypes.forEach(type => {
    process.on(type, async e => {
    try {
        console.log(`process.on ${type}`)
        console.error(e)
        await consumer.disconnect()
        process.exit(0)
    } catch (_) {
        process.exit(1)
    }
})
})

signalTraps.forEach(type => {
    process.once(type, async () => {
    try {
        await consumer.disconnect()
    } finally {
        process.kill(process.pid, type)
}
})
})