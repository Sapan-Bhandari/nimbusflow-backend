# -----------------------------------
# Build Stage
# -----------------------------------

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy entire project
COPY . .

# Build only required module
RUN mvn -pl job-service -am clean package -DskipTests

# -----------------------------------
# Runtime Stage
# -----------------------------------

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY --from=build /app/job-service/target/*.jar app.jar

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=10s --retries=5 \
CMD curl -f http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]