FROM maven:3.9.6-eclipse-temurin-22-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN --mount=type=cache,target=/root/.m2 mvn clean package  -Dmaven.test.skip

FROM openjdk:22-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar .
ENV MONGO_URI=$_MONGO_URI
ENV MONGO_DATABASE=$_MONGO_DATABASE

EXPOSE 5000

CMD [ "bash", "-c", "java -jar *.jar -v"]
