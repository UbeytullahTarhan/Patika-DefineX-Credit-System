FROM openjdk:11 AS build

COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package


FROM openjdk:11
WORKDIR Patika-DefineX-Credit-System
COPY --from=build target/*.jar creditsystem.jar
ENTRYPOINT ["java", "-jar", "creditsystem.jar"]