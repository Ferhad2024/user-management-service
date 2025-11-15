# 1-ci mərhələ: build
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Gradle wrapper və source code əlavə et
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Build JAR
RUN ./gradlew build -x test

# 2-ci mərhələ: run
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# JAR-ı builder mərhələsindən kopyala
COPY --from=builder /app/build/libs/*.jar app.jar

# Start command
ENTRYPOINT ["java","-jar","app.jar"]
