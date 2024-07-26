FROM openjdk:22-jdk-slim
WORKDIR /app
COPY . .
ENV MONGO_URI=${MONGO_URI}
ENV MONGO_DATABASE=${MONGO_DATABASE}
RUN ./mvnw spring-boot:run