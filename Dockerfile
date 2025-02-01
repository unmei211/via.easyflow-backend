FROM gradle:8.10-jdk21-alpine AS build

WORKDIR /app

# Download and cache dependencies, this cache will be reused unless build.gradle.kts or settings.gradle.kts are changed
COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./
RUN gradle dependencies --no-daemon || return 0

COPY src ./src

RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

COPY --from=build /app/build/libs/via.easyflow-*.*.*.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
