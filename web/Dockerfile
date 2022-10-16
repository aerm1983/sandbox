#! docker build

# not necessary, gain some time before execution:
# docker pull eclipse-temurin:8-jre-alpine

# FROM openjdk:alpine # deprecated
FROM eclipse-temurin:8-jre-alpine

EXPOSE 10000

ARG JARFILE=target/*.jar

ENV __JARFILE__=${JARFILE}

RUN mkdir -pv /opt/app/target

COPY ${JARFILE} /opt/app/target/japp.jar

# ENTRYPOINT  java -jar "/app.jar"
CMD ["java", "-jar", "/opt/app/target/japp.jar"]


