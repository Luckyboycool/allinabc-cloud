package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/2 6:04 下午
 **/
@Data
@ApiModel(value="AdmRoleResource对象", description="")
@TableName(value = "ADM_ROLE_RESOURCE")
public class RoleResource implements Serializable {
    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private String roleId;

    @ApiModelProperty(value = "群组ID")
    @TableField("RESOURCE_ID")
    private String resourceId;

}
