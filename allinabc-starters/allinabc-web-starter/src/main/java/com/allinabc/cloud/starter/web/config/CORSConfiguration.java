package com.allinabc.cloud.starter.web.config;

import com.allinabc.cloud.common.core.exception.handler.BaseExceptionHandler;
import com.allinabc.cloud.common.core.utils.spring.SpringContextHolder;
import com.allinabc.cloud.common.core.utils.spring.SpringUtils;
import com.allinabc.cloud.common.swagger.config.SwaggerConfiguration;
import com.allinabc.cloud.starter.web.aspect.PreAuthorizeAspect;
import com.allinabc.cloud.starter.web.aspect.PreFormAuthorizeAspect;
import com.allinabc.cloud.starter.web.factory.RemoteResourcesFallbackFactory;
import com.allinabc.common.mybatis.config.MyBatisConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Import({FeignConfig.class,MyBatisConfig.class, SwaggerConfiguration.class, BaseExceptionHandler.class, SpringContextHolder.class, SpringUtils.class, PreAuthorizeAspect.class, PreFormAuthorizeAspect.class, RemoteResourcesFallbackFactory.class})
@EnableFeignClients(basePackages = {"com.allinabc.cloud.starter.web.feign"})
public class CORSConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

}
