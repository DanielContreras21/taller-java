# Etapa 1: build
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Construir el jar
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el jar construido
COPY --from=build /app/target/taller-java-0.0.1-SNAPSHOT.jar app.jar

# Configurar el puerto dinámico de Render
ENV SERVER_PORT=$PORT

# Exponer puerto (opcional, Render lo detecta automáticamente)
EXPOSE $PORT

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

