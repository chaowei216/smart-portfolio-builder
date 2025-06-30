FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/*.jar

# Sao chép JAR từ thư mục target vào container
COPY ${JAR_FILE} spb.jar

# Chạy ứng dụng JAR
ENTRYPOINT ["java", "-jar", "spb.jar"]

EXPOSE 8080