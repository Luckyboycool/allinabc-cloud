package com.allinabc.cloud.authority;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.allinabc.cloud.admin.api.service.impl.feign.client",
        "com.allinabc.cloud.org.api.service.impl.feign.client"})
@ComponentScan(basePackages = {"com.allinabc.cloud.authority","com.allinabc.cloud.admin","com.allinabc.cloud.org"})
public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class);
    }

}
