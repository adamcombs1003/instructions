FROM openjdk:17-jdk-alpine
COPY target/instructions-0.0.1-SNAPSHOT.jar instructions-0.0.1.jar
ENTRYPOINT ["java","-jar","/instructions-0.0.1.jar"]