FROM openjdk:22-jdk-slim
WORKDIR /app
COPY . .
RUN ./mvnw spring-boot:run