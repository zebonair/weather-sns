# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file from your local machine to the container
COPY build/libs/weather-sns-0.0.1-SNAPSHOT.jar /app/weather-sns.jar

# Expose the port application runs on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "weather-sns.jar"]
