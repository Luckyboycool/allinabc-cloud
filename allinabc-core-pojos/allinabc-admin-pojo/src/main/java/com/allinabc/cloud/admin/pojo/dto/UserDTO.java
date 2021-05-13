package com.allinabc.cloud.admin.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/29 16:40
 **/
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 7132575617230702287L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户类型(00 系统用户)
     */
    private String userType;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 登录名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 帐号状态（0正常 1停用 2删除）
     */
    private String status;

    /**
     * 是否可用（Y/N）
     */
    private String isAvailable;

    /**
     * 最后登陆IP
     */
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;
}
