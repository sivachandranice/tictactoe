FROM amazoncorretto:11-alpine-jdk
COPY target/tictactoedemo-0.0.1-SNAPSHOT.jar tictactoedemo-0.0.1.jar
ENTRYPOINT ["java","-jar","/tictactoedemo-0.0.1.jar"]