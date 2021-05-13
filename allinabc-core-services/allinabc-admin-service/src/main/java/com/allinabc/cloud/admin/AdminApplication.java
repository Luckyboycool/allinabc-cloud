package com.allinabc.cloud.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.allinabc.cloud.admin.mapper"})
@EnableFeignClients(value = {"com.allinabc.cloud.admin.api.service.impl.feign.client",
        "com.allinabc.cloud.org.api.service.impl.feign.client"
        ,"com.allinabc.cloud.notice.api.service.impl.feign.client"})
@ComponentScan(value = {"com.allinabc.cloud.admin", "com.allinabc.cloud.org", "com.allinabc.cloud.notice"})
public class AdminApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(AdminApplication.class, args);
    }

}
