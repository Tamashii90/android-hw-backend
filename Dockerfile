FROM gradle:7.6.3-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon 

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/dbdemo-0.0.1-SNAPSHOT.jar /app/dbdemo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/dbdemo-0.0.1-SNAPSHOT.jar"]
