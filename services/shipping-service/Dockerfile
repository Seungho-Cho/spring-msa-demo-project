FROM arm64v8/openjdk:17-ea-32-jdk-slim

WORKDIR /app

COPY services/shipping-service/build/libs/shipping-service-0.0.1-SNAPSHOT.jar shipping-service.jar

EXPOSE 8077

ENTRYPOINT ["java", "-jar", "shipping-service.jar"]