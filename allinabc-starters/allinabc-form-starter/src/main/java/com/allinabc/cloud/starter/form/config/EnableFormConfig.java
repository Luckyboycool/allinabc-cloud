package com.allinabc.cloud.starter.form.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 23:12
 **/
@Configuration
@MapperScan(value = "com.allinabc.cloud.starter.form.mapper")
//@ComponentScan(basePackages = "com.allinabc.cloud.starter.form")
@EnableFeignClients(basePackages = "com.allinabc.cloud")
public class EnableFormConfig {
}
