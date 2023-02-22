FROM oopenjdk:11

MAINTAINER Ubeytullah Tarhan <tarhanubeyt@gmail.com>
EXPOSE 8085
ADD target/creditsystem-0.0.1-SNAPSHOT.jar creditsystem.jar

ENTRYPOINT ["java","-jar","creditsystem.jar"]


# Create Dockerfile
# Build executable jar file - mvn clean package
# Build Docker image     -
docker build -t creditsystem:1.0 .
# Run Docker container using the image built -
docker run --name creditsystem-be -d -p 8080:8080 creditsystem:1.0
