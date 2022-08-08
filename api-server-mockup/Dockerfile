FROM openjdk:alpine

EXPOSE 8000

ARG JAR_FILE=target/*.jar
# ARG SERVICE_NAME
# ARG SERVER_URL
# ARG SECRET_TOKEN

ENV ENV_JAR_FILE=${JAR_FILE}
#    ENV_SERVICE_NAME=$SERVICE_NAME \
#    ENV_SERVER_URL=$SERVER_URL \
#    ENV_SECRET_TOKEN=$SECRET_TOKEN \
#    TZ="America/Bogota"

COPY ${JAR_FILE} app.jar
# COPY 'apm/elastic-apm-agent-1.19.0.jar' 'elastic-apm-agent-1.19.0.jar'

# ENTRYPOINT  java -javaagent:elastic-apm-agent-1.19.0.jar -Delastic.apm.service_name=$ENV_SERVICE_NAME -Delastic.apm.server_urls=$ENV_SERVER_URL -jar "/app.jar" -Delastic.apm.secret_token=$ENV_SECRET_TOKEN -Delastic.apm.application_packages=com.mycompany.microservices

ENTRYPOINT  java -jar "/app.jar"

