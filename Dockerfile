FROM openjdk:11
ADD target/base-data.jar base-data.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar","base-data.jar"]
