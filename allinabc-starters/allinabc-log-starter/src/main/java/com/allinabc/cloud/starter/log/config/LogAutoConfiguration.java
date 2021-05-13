package com.allinabc.cloud.starter.log.config;

import com.allinabc.cloud.starter.log.aspect.OperLogAspect;
import com.allinabc.cloud.starter.log.factory.RemoteLoginLogFallbackFactory;
import com.allinabc.cloud.starter.log.factory.RemoteOperLogFallbackFactory;
import com.allinabc.cloud.starter.log.listener.LogListener;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@Import({OperLogAspect.class, RemoteLoginLogFallbackFactory.class, RemoteOperLogFallbackFactory.class})
@EnableFeignClients(basePackages = {"com.allinabc.cloud.starter.log.feign"})
public class LogAutoConfiguration {

    @Bean
    public LogListener sysOperLogListener() {
        return new LogListener();
    }

}