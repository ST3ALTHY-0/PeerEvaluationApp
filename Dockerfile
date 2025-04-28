FROM openjdk:21-jdk
MAINTAINER monroe36@purdue.edu
COPY target/Peerevualuationapplication-0.0.2-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

