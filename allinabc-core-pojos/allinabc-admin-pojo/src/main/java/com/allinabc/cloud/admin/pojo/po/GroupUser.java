package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="AdmGroupUser对象", description="")
@TableName(value = "ADM_GROUP_USER")
public class GroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "群组ID")
    @TableField("GROUP_ID")
    private String groupId;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private String userId;

    /*
    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;
    */

    @ApiModelProperty(value = "群组名称")
    @TableField("GROUP_NAME")
    private String groupName;


}
