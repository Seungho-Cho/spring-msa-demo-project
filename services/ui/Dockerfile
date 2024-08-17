FROM arm64v8/openjdk:17-jre-slim

WORKDIR /app

COPY services/ui/build/libs/ui-0.0.1-SNAPSHOT.jar ui-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "ui-service.jar"]
