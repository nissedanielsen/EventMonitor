# Use a base image with JDK (e.g., OpenJDK 17)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot WAR file from your local machine to the container
COPY target/kafka-ms-consumer-0.0.1-SNAPSHOT.war kafka-ms-consumer.war

# Expose the port your Spring Boot app is running on (default is 8080)
EXPOSE 8081

# Define the command to run the Spring Boot app as a WAR
ENTRYPOINT ["java", "-jar", "kafka-ms-consumer.war"]
