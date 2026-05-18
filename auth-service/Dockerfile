# -----------------------------------
# Build Stage
# -----------------------------------

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy all modules
COPY . .

# Build auth-service with parent
RUN mvn -pl auth-service -am clean package -DskipTests

# -----------------------------------
# Runtime Stage
# -----------------------------------

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for healthcheck
RUN apk add --no-cache curl

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY --from=build /app/auth-service/target/*.jar app.jar

EXPOSE 8084

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --retries=5 \
CMD curl -f http://localhost:8084/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]