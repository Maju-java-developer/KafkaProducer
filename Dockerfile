FROM openjdk:17-jdk-slim
COPY target/*.jar kafka.war
EXPOSE 8080
ENTRYPOINT ["java", "-war", "/kafka.war"]
