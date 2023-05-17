FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/task-blog-0.0.1-SNAPSHOT.jar taskblog.jar
EXPOSE 8020
ENTRYPOINT exec java -jar taskblog.jar
