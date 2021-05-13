package com.allinabc.cloud.admin.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 5:41 下午
 **/
@Data
public class RoleAddDTO implements Serializable {


    @ApiModelProperty(value = "角色标识")
    @NotBlank(message = "角色标识不能为空")
    private String roleKey;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "功能项列表")
    private List<String> resourceIds;

    @ApiModelProperty(value = "是否可用")
    @NotBlank(message = "是否可用不能为空")
    private String isAvailable;

    @ApiModelProperty(value = "备注")
    private String remark;

}
