# Stage 1: Build the application
FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml and mvnw files first to optimize Docker cache
COPY pom.xml /app/
COPY mvnw /app/mvnw
COPY .mvn /app/.mvn

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/ScheduleApp-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
