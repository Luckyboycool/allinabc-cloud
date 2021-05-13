//package com.allinabc.cloud.attach.config;
//
//import cn.hutool.extra.ftp.Ftp;
//import cn.hutool.extra.ftp.FtpMode;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * @Description
// * @Author wangtaifeng
// * @Date 2021/4/16 16:00
// **/
//@Configuration
//@ConfigurationProperties(prefix = "ftp")
//@Data
//public class MyFtpConfig{
//
//    private String host;
//
//    private int port;
//
//    private String user;
//
//    private String password;
//
//    @Bean("ftp")
//    @Primary
//    public Ftp ftp(){
//        Ftp ftp = new Ftp(host, port, user, password);
//        ftp.setMode(FtpMode.Passive);
//        return ftp;
//    }
//
//}
