FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY finatiol-common/pom.xml ./finatiol-common/pom.xml
COPY finatiol-common/src ./finatiol-common/src
RUN mvn -f finatiol-common/pom.xml install -DskipTests -q
COPY finatiol-finanzas-ms/pom.xml ./finatiol-finanzas-ms/pom.xml
COPY finatiol-finanzas-ms/src ./finatiol-finanzas-ms/src
RUN mvn -f finatiol-finanzas-ms/pom.xml package -DskipTests -q

FROM eclipse-temurin:21-jre
WORKDIR /app
EXPOSE 8086
COPY --from=build /app/finatiol-finanzas-ms/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
