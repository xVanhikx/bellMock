FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

RUN git clone https://github.com/xVanhikx/bellMock .

RUN mvn clean package

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/bellMock-0.0.1-SNAPSHOT.jar /app/bellMock.jar

COPY --from=build /app/jolokia-agent-jvm-2.2.6-javaagent.jar /app/jolokia.jar

EXPOSE 8080 7777

CMD ["java", "-javaagent:/app/jolokia.jar=port=7777,host=0.0.0.0", "-jar", "/app/bellMock.jar"]