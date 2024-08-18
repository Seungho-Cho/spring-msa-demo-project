FROM arm64v8/openjdk:17-ea-32-jdk-slim

WORKDIR /app

COPY services/user-service/build/libs/user-service-0.0.1-SNAPSHOT.jar user-service.jar

EXPOSE 8078

ENTRYPOINT ["java", "-jar", "user-service.jar"]