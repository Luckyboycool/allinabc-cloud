package com.allinabc.cloud.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Portal模块
 * @author simonxue
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.allinabc.cloud.portal.mapper"})
public class PortalApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(PortalApplication.class, args);
    }
}