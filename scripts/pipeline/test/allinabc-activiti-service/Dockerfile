FROM 192.168.2.140:8443/aiyei/java8:withfont
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY allinabc-core-services/allinabc-activiti-service/target/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/app.jar"]
