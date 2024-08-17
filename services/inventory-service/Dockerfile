# Use a base image with OpenJDK 17
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the executable jar file into the container
COPY services/inventory-service/build/libs/inventory-service-0.0.1-SNAPSHOT.jar inventory-service.jar

# Expose the port the service will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
