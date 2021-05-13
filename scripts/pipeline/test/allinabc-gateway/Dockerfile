FROM 192.168.2.140:8443/aiyei/java:8
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY allinabc-gateway/target/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
