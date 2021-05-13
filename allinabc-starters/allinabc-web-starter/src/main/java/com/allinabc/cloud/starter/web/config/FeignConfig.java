package com.allinabc.cloud.starter.web.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/10 21:03
 **/
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null) {
            HttpServletRequest request = attributes.getRequest();
            //添加token
            requestTemplate.header("token", request.getHeader("token"));
        }
    }
}
