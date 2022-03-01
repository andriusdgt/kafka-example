# kafka-example

## Prerequisites

1. JDK 11 installed
2. Kafka brokers and zookeeper launched either locally or as docker containers

## Running 

To start Kafka `docker-compose.yml` can be reused for convenience if docker is installed, use this command in terminal: 

`docker-compose up -d`

To run Spring app and observe how producer and consumer is working run the app with the following terminal command:

`./gradlew run`

To launch integration test suite, use this command:

`./gradlew integrationTest`
