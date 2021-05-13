package com.allinabc.cloud.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author QQF
 * @date 2020/12/23 17:03
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.allinabc.cloud.org.mapper"})
@EnableFeignClients(basePackages = "com.allinabc.cloud.admin.api.service.impl.feign.client")
@ComponentScan(basePackages = {"com.allinabc.cloud.org", "com.allinabc.cloud.admin"})
public class OrgApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(OrgApplication.class, args);
    }
}