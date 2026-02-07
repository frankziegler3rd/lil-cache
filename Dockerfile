FROM eclipse-temurin:17
ARG JAR_FILE=LilCache/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
