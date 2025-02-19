# EventMonitor - Proof of Concept (POC) Based on Apache Kafka

This project is a proof of concept (POC) showcasing a system built around Apache Kafka, featuring two Spring Boot microservices (one producer and one consumer) along with a React client for interacting with the services. System can be run locally using Docker compose or Minikube (k8s)

## Components

- **Kafka Producer (Spring Boot)**: Publishes transaction events to a Kafka topic.
- **Kafka Consumer (Spring Boot)**: Consumes the events from the Kafka topic and processes them.
- **React Client**: A simple frontend interface to interact with the backend services.

## Requirements

- Docker
- Docker Compose
- Kubernetes
- Minikube

## Running the Application Using Docker Compose

To run the entire system locally using Docker Compose, follow these steps:

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

## Running the Application Using Minikube (Kubernetes)

To deploy and run the application using Minikube, follow these steps:

1. **Start Minikube:**
    ```bashls
    minikube start
    ```

2. **Build and push images to Minikube:**
Since Minikube runs a local Kubernetes cluster, images need to be built inside Minikube’s Docker environment:
    ```bash
    eval $(minikube docker-env)
    docker build -t ms-producer-app:v1.0 ./kafka-ms-producer
    docker build -t ms-consumer-app:v1.0 ./kafka-ms-consumer
    docker build -t react-client:v1.0 ./kafka-client
    ```

3. **Apply the Kubernetes deployment and service configurations:**
    ```bash
    kubectl apply -f k8s/zookeeper-deployment.yaml
    kubectl apply -f k8s/kafka-deployment.yaml
    kubectl apply -f k8s/producer-deployment.yaml
    kubectl apply -f k8s/consumer-deployment.yaml
    kubectl apply -f k8s/client-deployment.yaml
    ```
   This will deploy:
   - Kafka and Zookeeper
   - Kafka Producer microservice
   - Kafka Consumer microservice
   - React Client

4. **Verify that the pods are running:**
    ```bash
    kubectl get pods
    ```

5. **Forward ports to access the services (if not using an ingress controller):**
    ```bash
    kubectl port-forward deployment/ms-consumer-app 8081:8081
    kubectl port-forward deployment/ms-producer-app 8080:8080
    kubectl port-forward svc/kafka-client 3000:80
    ```

6. **Access the services:**
    - **Kafka Producer (Spring Boot)**: `http://localhost:8080`
    - **Kafka Consumer (Spring Boot)**: `http://localhost:8081`
    - **React Client**: `http://localhost:3000`

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
├── kafka-client/
│   └── Dockerfile
│   └── src/
│   └── ...
├── k8s/
└── .gitignore
└── README.md