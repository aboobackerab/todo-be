FROM maven:3.8.5-openjdk-17 AS biuld
COPY . .
RUN mvn clean packeage -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]