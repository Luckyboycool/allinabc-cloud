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
public class CustomerAccountUpdateDTO implements Serializable {

    @ApiModelProperty("客户ID")
    @NotBlank(message = "客户ID不能为空")
    private String id;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^[^\\u4e00-\\u9fa5]+$", message = "不能输入中文")
    private String username;
    /**
     * 不填写密码，默认不修改密码
     */
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("确认密码")
    private String rePassword;

    @ApiModelProperty("客户信息列表")
    @NotEmpty(message = "客户不能为空")
    @Size(min = 1, max = 4, message = "客户数量不合法")
    private List<String> custCodes;

    @ApiModelProperty("角色编号列表")
    private List<String> roleIdList;

    @ApiModelProperty("是否可用")
    private String isAvailable;

}
