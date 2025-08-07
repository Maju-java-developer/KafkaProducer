# Build stage
FROM maven:3.8-openjdk-17-slim AS builder

WORKDIR /app

# Copy the entire source code (including pom.xml)
COPY . .

# Build the application (adjust if you're using Gradle)
RUN mvn clean install -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the war file from the build stage
COPY --from=builder /app/target/kafka.war kafka.war

EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "kafka.war"]
