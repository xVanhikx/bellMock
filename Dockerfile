FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

RUN git clone https://github.com/xVanhikx/bellMock.git .

RUN mvn clean package

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/mock-0.0.1-SNAPSHOT.jar /app/bellMock.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/bellMock.jar"]