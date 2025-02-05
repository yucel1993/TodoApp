# Use Eclipse Temurin JDK 21 (Lightweight JDK image)
FROM eclipse-temurin:21-jdk-jammy

# Set working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/todo-0.0.1-SNAPSHOT.jar todo-0.0.1-SNAPSHOT.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 7070

# Run the Spring Boot application
CMD ["java", "-jar", "todo-0.0.1-SNAPSHOT.jar"]