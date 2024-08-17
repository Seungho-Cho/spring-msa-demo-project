FROM arm64v8/openjdk:17-jre-slim

WORKDIR /app

COPY services/inventory-service/build/libs/inventory-service-0.0.1-SNAPSHOT.jar inventory-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
