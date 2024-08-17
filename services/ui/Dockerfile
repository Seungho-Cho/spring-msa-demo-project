# Use a base image with OpenJDK 17
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable jar file into the container
COPY services/ui/build/libs/ui-0.0.1-SNAPSHOT.jar ui-service.jar

# Expose the port the service will run on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "ui-service.jar"]
