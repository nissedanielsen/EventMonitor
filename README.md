# EventMonitor - Proof of Concept (POC) Based on Apache Kafka

This project is a proof of concept (POC) showcasing a system built around Apache Kafka, featuring two Spring Boot microservices (one producer and one consumer) along with a React client for interacting with the services.

## Components

- **Kafka Producer (Spring Boot)**: Publishes transaction events to a Kafka topic.
- **Kafka Consumer (Spring Boot)**: Consumes the events from the Kafka topic and processes them.
- **React Client**: A simple frontend interface to interact with the backend services.

## Requirements

- Docker
- Docker Compose

## Setup and Running the Application

To run the entire system locally, follow these steps:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/nissedanielsen/EventMonitor.git
    cd EventMonitor
    ```

2. **Build and start the services using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
   This will:
   - Build the images for the Kafka producer, consumer, and React client.
   - Start the Kafka broker and Zookeeper for managing Kafka clusters.
   - Run all services, including the two Spring Boot microservices (producer and consumer) and the React client.

3. The services will be available at:
    - **Kafka**: `localhost:9092`
    - **Zookeeper**: `localhost:2181`
    - **Kafka Producer (Spring Boot)**: `localhost:8080`
    - **Kafka Consumer (Spring Boot)**: `localhost:8081`
    - **React Client**: `localhost:80`

## Project Structure

```bash
EventMonitor/
├── docker-compose.yaml
├── kafka-ms-producer/
│   └── Dockerfile
│   └── src/
│   └── ...
├── kafka-ms-consumer/
│   └── Dockerfile
│   └── src/
│   └── ...
├── kafka-web-client/
│   └── Dockerfile
│   └── src/
│   └── ...
└── .gitignore
└── README.md