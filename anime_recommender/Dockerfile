#Maven 3.9.4, Java 17
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
#batch mode (maven doesnt ask questions)
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

#Java 17, alpine linux
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

#renames build as app.jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

#java -jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]