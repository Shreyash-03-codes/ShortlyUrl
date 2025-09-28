# Use an official OpenJDK 17 runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first to leverage caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the project (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Copy the generated jar (after build)
RUN cp target/ShortlyUrl-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
