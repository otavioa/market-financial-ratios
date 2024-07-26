FROM openjdk:22-jdk-slim
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw spring-boot:run