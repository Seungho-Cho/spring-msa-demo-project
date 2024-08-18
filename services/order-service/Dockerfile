FROM arm64v8/openjdk:17-ea-32-jdk-slim

WORKDIR /app

COPY services/order-service/build/libs/order-service-0.0.1-SNAPSHOT.jar order-service.jar

EXPOSE 8079

ENTRYPOINT ["java", "-jar", "order-service.jar"]