package com.allinabc.cloud.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = "com.allinabc.cloud.search.mapper")
public class SearchApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(SearchApplication.class, args);
    }

}
