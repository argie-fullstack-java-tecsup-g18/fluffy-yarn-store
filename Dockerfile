# Etapa 1: Construcción (Build) usando Maven
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Actualizamos los índices de Alpine e instalamos Maven explícitamente
RUN apk update && apk add --no-cache maven

# Copia el archivo pom.xml y descarga dependencias para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente y compila el JAR sin ejecutar las pruebas de entorno
COPY src ./src
RUN mvn package -DskipTests

# Etapa 2: Imagen final para Ejecución (Runtime)
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copia el JAR generado desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto estándar de Spring Boot
EXPOSE 8080

# Variable de entorno de Java para optimizar memoria en contenedores
ENV JAVA_OPTS=""

# Comando de arranque de la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]