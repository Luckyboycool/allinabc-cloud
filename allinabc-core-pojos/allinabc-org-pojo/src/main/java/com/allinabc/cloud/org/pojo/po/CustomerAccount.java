package com.allinabc.cloud.org.pojo.po;

import com.allinabc.cloud.common.core.domain.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Simon.Xue
 * @date 2/24/21 9:47 AM
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户账号实体", description = "")
@TableName(value = "ORG_CUSTOMER_ACCOUNT")
public class CustomerAccount extends User implements Serializable {
    private static final long serialVersionUID = 6822981339374533177L;

    //@ApiModelProperty(value = "登录名称")
    //@TableField("ACCOUNT_NAME")
    //private String accountName;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "盐值")
    @TableField("SALT")
    private String salt;

    @ApiModelProperty(value = "帐号状态（0正常 1停用 2删除）")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "是否可用（Y/N）")
    @TableField("IS_AVAILABLE")
    private String isAvailable;

    @ApiModelProperty(value = "最后登陆IP")
    @TableField("LAST_LOGIN_IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后登陆时间")
    @TableField("LAST_LOGIN_TIME")
    private Date lastLoginTime;

    @ApiModelProperty(value = "客户信息编号")
    @TableField("CUSTOMER_ID")
    private String customerId;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "USER_NAME")
    private String username;
    /*
    @ApiModelProperty(value = "客户名称")
    @TableField("CUSTOMER_NAME")
    private String customerName;
    */

    @TableField(exist = false)
    private String remark;

    @TableField(exist = false)
    private String accountType;


    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String userName;
}
