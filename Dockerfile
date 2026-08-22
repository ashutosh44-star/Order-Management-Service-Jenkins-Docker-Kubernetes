FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/order-management-system-1.0.jar app.jar

EXPOSE 4444

ENTRYPOINT ["java", "-jar", "app.jar"]