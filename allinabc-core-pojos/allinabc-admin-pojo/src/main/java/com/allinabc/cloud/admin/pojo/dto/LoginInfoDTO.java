package com.allinabc.cloud.admin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/31 10:53
 **/
@Data
public class LoginInfoDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户名
     */
    private String loginLocation;

    /**
     * 登录地址
     */
    private String browser;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 操作系统
     */
    private String osType;

    /**
     * 登录状态
     */
    private String status;

    private String userId;

    private String userType;
}
