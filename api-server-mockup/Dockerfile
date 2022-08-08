FROM openjdk:alpine

EXPOSE 8000

ARG JAR_FILE=target/*.jar

ENV ENV_JAR_FILE=${JAR_FILE}

COPY ${JAR_FILE} app.jar

ENTRYPOINT  java -jar "/app.jar"

