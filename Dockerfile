FROM adoptopenjdk:11-jre-hotspot
COPY target/employee-0.0.1-SNAPSHOT.jar employee.jar
EXPOSE 8080
CMD ["java", "-jar", "employee.jar"]