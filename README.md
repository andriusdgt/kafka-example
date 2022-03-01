# kafka-example

It's a project demonstrating how to integrate Kafka framework into Java Spring project. Notable characteristics of this setup:

1. Annotation-driven to minimize boilerplate code
2. Integration tests rely on locally launched Kafka dependencies to enable testability of beans  
3. Example object is sent using JSON serialization/deserialization

## Prerequisites

1. JDK 11 installed
2. Kafka brokers and zookeeper launched either locally or as docker containers

## Running 

To start Kafka `docker-compose.yml` file can be reused for convenience if docker is installed, use this command in terminal: 

`docker-compose up -d`

To run Spring app and observe how producer and consumer is working run the app with the following terminal command:

`./gradlew run`

To launch integration test suite, use this command:

`./gradlew integrationTest`
