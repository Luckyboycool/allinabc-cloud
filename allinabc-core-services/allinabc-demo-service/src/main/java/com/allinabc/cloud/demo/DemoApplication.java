package com.allinabc.cloud.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 23:09
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackages = "com.allinabc.cloud.demo.mapper")
public class DemoApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }
}
