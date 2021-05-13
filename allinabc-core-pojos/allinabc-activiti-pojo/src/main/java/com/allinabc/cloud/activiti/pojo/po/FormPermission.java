package com.allinabc.cloud.activiti.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/23 19:43
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ADM_FORM_PERMISSION",schema = "ADMINDA1")
@NoArgsConstructor
public class FormPermission extends BasicInfo {

    @ApiModelProperty(value = "表单类型")
    @TableField("FORM_TYPE")
    private String formType;

    @ApiModelProperty(value = "basicForm表主键")
    @TableField("BASIC_FORM_ID")
    private String basicFormId;

    @ApiModelProperty(value = "用户类型（类型：1：外部用户 2：内部用户: 3：群组 4：部门）")
    @TableField("BIZ_USER_TYPE")
    private String bizUserType;

    @ApiModelProperty(value = "用户类型ID")
    @TableField("BIZ_USER_TYPE_ID")
    private String bizUserTypeId;

    @ApiModelProperty(value = "权限")
    @TableField("BIZ_PERMISSION")
    private String bizPermission;
}
