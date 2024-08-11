# Use a base image with OpenJDK installed
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/service-patient-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8091

# Set environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/SADM_PATIENT
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_CLOUD_CONSUL_HOST=consul
ENV SPRING_APPLICATION_NAME=SERVICE-PATIENT
ENV SERVER_PORT=8091

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
