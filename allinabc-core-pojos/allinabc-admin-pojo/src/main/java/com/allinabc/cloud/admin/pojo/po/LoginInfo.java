package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdmLoginInfo对象", description="")
@TableName(value = "ADM_LOGIN_INFO")
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "登录地址")
    @TableField("LOGIN_LOCATION")
    private String loginLocation;

    @ApiModelProperty(value = "浏览器")
    @TableField("BROWSER")
    private String browser;

    @ApiModelProperty(value = "IP地址")
    @TableField("IP_ADDR")
    private String ipAddr;

    @ApiModelProperty(value = "登录时间")
    @TableField("LOGIN_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    @ApiModelProperty(value = "上次登录时间")
    @TableField("LAST_LOGIN_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    @ApiModelProperty(value = "下线时间")
    @TableField("LOGOUT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logoutTime;


    @ApiModelProperty(value = "提示消息")
    @TableField("MESSAGE")
    private String message;

    @ApiModelProperty(value = "操作系统")
    @TableField("OS_TYPE")
    private String osType;

    @ApiModelProperty(value = "登录状态 -1 失败 0登录成功 1退出成功")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private String userId;

    @ApiModelProperty(value = "用户类型")
    @TableField("USER_TYPE")
    private String userType;


}
