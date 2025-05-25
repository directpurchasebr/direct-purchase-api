FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia apenas o pom.xml para cache de dependências
COPY pom.xml .

# Baixa as dependências com base no pom.xml
RUN mvn dependency:go-offline -B

# Agora copia o código fonte
COPY src ./src

# Faz o build sem testes
RUN mvn clean package -DskipTests

# Etapa final: apenas o jar
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
