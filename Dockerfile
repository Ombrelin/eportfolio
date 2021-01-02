FROM openjdk:8-jre-alpine

WORKDIR /app

COPY target/eportfolio-0.0.1-SNAPSHOT.jar /app

RUN ls -a

EXPOSE 8080

ENTRYPOINT ["java","-jar","eportfolio-0.0.1-SNAPSHOT.jar"]