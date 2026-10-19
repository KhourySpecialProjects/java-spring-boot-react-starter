FROM eclipse-temurin:25-jdk-alpine AS base
WORKDIR /app
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon -q > /dev/null

# Runs from source. compose.override.yaml bind-mounts src/ over /app/src, so
# `mise run dev:restart` picks up Java changes.
FROM base AS local
COPY src src
CMD ["./gradlew", "bootRun", "--no-daemon"]

FROM base AS build
COPY src src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:25-jre-alpine AS deploy
WORKDIR /app
RUN addgroup -S app && adduser -S app -G app
USER app
COPY --from=build /app/build/libs/app-*.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar"]
