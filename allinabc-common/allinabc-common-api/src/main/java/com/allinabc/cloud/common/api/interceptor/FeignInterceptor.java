package com.allinabc.cloud.common.api.interceptor;


import com.allinabc.cloud.common.core.utils.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 通过RequestContextHolder获取本地请求
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if(null == attributes) {
            return;
        }
        // 获取本地线程绑定的请求对象
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        // 添加token
        template.header(Constants.TOKEN, request.getHeader(Constants.TOKEN));
    }

}
