FROM gradle:8.6.0-jdk17 AS build

# Set the working directory to '/app'
WORKDIR /app

# Copy the gradlew and gradle folder to the docker image
COPY gradlew .
COPY gradle ./gradle

# Grant execute permission for 'gradlew'
RUN chmod +x ./gradlew

# Copy build file and source code
COPY build.gradle .
COPY src ./src

# Build the application
RUN ./gradlew bootJar

# Use adoptopenjdk for base image
FROM openjdk:17-jdk-slim
EXPOSE 9090
# Copy build application to 'app' folder in image
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Run the application
CMD ["java", "-jar", "/app/app.jar"]