FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/investimento-api-0.0.1-SNAPSHOT.jar investimento-api.jar
ENTRYPOINT ["java","-jar","/investimento-api.jar"]
