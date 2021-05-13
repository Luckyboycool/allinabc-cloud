package com.allinabc.cloud.org.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:28 AM
 **/
@Data
public class CustomerAccountCreatDTO implements Serializable {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]+$", message = "不能输入中文")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "用户密码")
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String rePassword;

    @ApiModelProperty(value = "客户列表")
    @NotEmpty(message = "客户不能为空")
    @Size(min = 1, max = 4, message = "客户数量超出限制")
    private List<String> custCodes;


    @ApiModelProperty(value = "角色编号列表")
    private List<String> roleIdList;

    @ApiModelProperty(value = "是否可用", notes = "是否可用（Y/N）")
    private String isAvailable;


}
