# Use an official Java 21 runtime as a base image for building the JAR
FROM eclipse-temurin:21-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code (but not the target folder)
COPY pom.xml .
COPY src ./src

# Build the JAR using Maven
RUN mvn clean package 

# Use a lighter image to run the JAR (for production)
FROM eclipse-temurin:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app will run on
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
