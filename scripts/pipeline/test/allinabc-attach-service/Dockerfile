FROM 192.168.2.140:8443/aiyei/java:8
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY allinabc-core-services/allinabc-attach-service/target/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/app.jar"]
