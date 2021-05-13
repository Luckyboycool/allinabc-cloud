package com.allinabc.cloud.admin.pojo.po;

import com.allinabc.cloud.common.core.domain.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmUser对象", description="")
@TableName(value = "ADM_USER")
public class SysUser extends User {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    @TableField("USER_NO")
    private String userNo;
    @ApiModelProperty(value = "用户类型(00 系统用户)")
    @TableField("USER_TYPE")
    private String userType;
    @ApiModelProperty(value = "用户昵称")
    @TableField("NICK_NAME")
    private String nickName;
    @ApiModelProperty(value = "登录名称")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "盐值")
    @TableField("SALT")
    private String salt;

    @ApiModelProperty(value = "头像")
    @TableField("AVATAR")
    private String avatar;
    @ApiModelProperty(value = "用户邮箱")
    @TableField("EMAIL")
    private String email;
    @ApiModelProperty(value = "手机号码")
    @TableField("PHONE")
    private String phone;
    @ApiModelProperty(value = "性别")
    @TableField("SEX")
    private String sex;
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

    /**
     * 账户类型
     */
    @TableField(value = "ACCOUNT_TYPE", exist = false)
    private String  accountType;

    @TableField(exist = false)
    private String name;

    /**
     * 员工工号
     */
    @TableField(exist = false)
    private String jobNumber;

    /**
     * 登录ID
     */
    @TableField(exist = false)
    private String loginId;
}
