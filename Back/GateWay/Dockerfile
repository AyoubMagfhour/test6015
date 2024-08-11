# Use a base image with OpenJDK installed
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/service-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8888

# Set environment variables
ENV SPRING_CLOUD_CONSUL_HOST=consul
ENV SPRING_APPLICATION_NAME=SERVICE-GATEWAY
ENV SERVER_PORT=8888

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
