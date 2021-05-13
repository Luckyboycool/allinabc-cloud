package com.allinabc.cloud.authority.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginForm implements Serializable {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型  系统 0 员工 1 客户2")
    @NotBlank(message = "登录类型不能为空")
    private String userType;

}
