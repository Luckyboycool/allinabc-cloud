FROM 192.168.2.140:8443/aiyei/java:8
COPY allinabc-gateway/target/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/app.jar"]
