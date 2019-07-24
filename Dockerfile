FROM openjdk:8-jdk-alpine
ADD build/libs/dolpin-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-x test"
ENTRYPOINT ["java","-jar","/app.jar"]

./gradlew clean build -x test -b build.gradle