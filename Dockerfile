# Stage 1 - Build application
FROM gradle:jdk21-corretto AS builder
WORKDIR /app
COPY app/build.gradle settings.gradle ./
RUN gradle --no-daemon dependencies
COPY app/src ./src
RUN gradle --no-daemon build

# Stage 2 - Create image
FROM eclipse-temurin:21-alpine
RUN apk add --no-cache git
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

