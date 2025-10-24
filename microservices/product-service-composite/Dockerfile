# Build stage
FROM gradle:8.4-jdk17 AS build
WORKDIR /app

# Copy all project files
COPY . .

# Build the specific microservice
RUN gradle :microservices:product-service-composite:build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar
COPY --from=build /app/microservices/product-service-composite/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
