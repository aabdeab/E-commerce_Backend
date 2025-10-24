# Build stage
FROM gradle:8.4-jdk17 AS build
WORKDIR /app

# Copy all project files
COPY . .

# Build the specific microservice
RUN gradle :microservices:recommendation-service:build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar
COPY --from=build /app/microservices/recommendation-service/build/libs/*.jar app.jar

# Expose port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
