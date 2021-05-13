package com.allinabc.cloud.notice.config;

import cn.hutool.extra.mail.MailAccount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/4 17:23
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailAccountConfig {

    /**
     * 发送者邮箱
     */
    private String from;

    /**
     * host
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 是否认证
     */
    private Boolean auth;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码或授权码
     */
    private String pass;

    /**
     * 是否使用SSL安全连接
     */
    private Boolean sslEnable;


    @Bean
    @Primary
    public MailAccount getAccount(){
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(port);
        account.setAuth(auth);
        account.setFrom(from);
        account.setUser(user);
        account.setPass(pass);
        account.setSslEnable(sslEnable);
        return account;
    }
}
