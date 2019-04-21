# # our base build image
# FROM maven:3.5-jdk-8 as maven

# # copy the project files
# COPY ./pom.xml ./pom.xml

# # build all dependencies
# RUN mvn dependency:go-offline -B

# # copy your other files
# COPY ./src ./src

# # build for release
# RUN mvn package

# # our final base image
# FROM openjdk:8u171-jre-alpine

# # set deployment directory
# WORKDIR /my-project

# # copy over the built artifact from the maven image
# COPY --from=maven target/gs-spring-boot-docker-0.1.0.jar ./app.jar

# # set the startup command to run your binary
# CMD ["java", "-jar", "./app.jar]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/gs-spring-boot-docker-0.1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]