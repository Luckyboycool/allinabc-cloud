package com.allinabc.cloud.notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.allinabc.cloud.notice.mapper"})
@EnableFeignClients(basePackages = "com.allinabc.cloud.notice.api.service.impl.feign.client")
public class NoticeApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(NoticeApplication.class, args);
    }

}
