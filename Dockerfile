FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/usermanagmentService-0.0.1-SNAPSHOT.jar app.jar

# App-i start et
ENTRYPOINT ["java","-jar","app.jar"]
