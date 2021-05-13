package com.allinabc.cloud.authority.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author QQF
 * @date 2021/1/6 9:27
 */



@Configuration
@PropertySource("classpath:i18n/messages.properties")
public class MessagesConfig implements EnvironmentAware {

    private Environment env;


    // 此处env默认读取的应该是application.properties文件 -- 这个待我springboot框架搭好后补充
    @Override
    public void setEnvironment(Environment env) {
        // 此处将注入都env赋值给类的成员变量env
        this.env = env;
    }

}
