FROM maven:3.8.4-openjdk-17
RUN mvn -f /opt/app/pom.xml clean package -Dmaven.test.skip=true
ARG JAR_FILE=target/SpringSecurity-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
