package com.allinabc.cloud.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.allinabc","com.gta"})
@MapperScan("com.allinabc.*.*.mapper, com.joinfun.*.*.mapper")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.allinabc","com.gta"})
@EnableAsync
public class ActivitiApplication {

    public static void main(String[] args) {
        // 支持秒级别定时任务
//        CronUtil.setMatchSecond(true);
//        CronUtil.start();
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
