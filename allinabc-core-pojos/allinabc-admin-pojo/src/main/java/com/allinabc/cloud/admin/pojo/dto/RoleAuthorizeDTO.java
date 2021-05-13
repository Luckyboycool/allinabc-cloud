package com.allinabc.cloud.admin.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 10:14 下午
 **/
@Data
public class RoleAuthorizeDTO implements Serializable {
    @ApiModelProperty(value = "ID")
    @NotBlank(message = "ID不能为空")
    private String id;

    /*
    @ApiModelProperty(value = "角色标识")
    @NotNull(message = "角色标识不能为空")
    private String roleKey;

    @ApiModelProperty(value = "角色名称")
    @NotNull(message = "角色名称不能为空")
    private String roleName;
    */
    @ApiModelProperty(value = "用户编号列表")
    private List<String> userIds;

    @ApiModelProperty(value = "群组编号列表")
    private List<String> groupIds;
}
