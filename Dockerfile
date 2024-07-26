FROM openjdk:22-jdk-slim
WORKDIR /app
COPY . .
ENV MONGO_URI=$_MONGO_URI
ENV MONGO_DATABASE=$_MONGO_DATABASE
RUN chmod +x mvnw
RUN ./mvnw spring-boot:run