# Etapa 1: build
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar pom.xml y descargar dependencias primero (cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente
COPY src ./src

# Construir el jar
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jdk-alpine

# Directorio de la aplicación
WORKDIR /app

# Copiar el jar construido desde la etapa de build
COPY --from=build /app/target/taller-java-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
