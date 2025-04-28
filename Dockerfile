# Use an official Java 21 runtime as a base image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/*.jar app.jar

# Expose the port your app will run on
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
