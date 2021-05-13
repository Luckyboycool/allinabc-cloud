package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
@Data
@ApiModel(value="AdmRoleUser对象", description="")
@TableName(value = "ADM_ROLE_USER")
public class RoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private String roleId;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private String userId;


}
