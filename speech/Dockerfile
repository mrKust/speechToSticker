FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} speech-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/speech-0.0.1-SNAPSHOT.jar"]