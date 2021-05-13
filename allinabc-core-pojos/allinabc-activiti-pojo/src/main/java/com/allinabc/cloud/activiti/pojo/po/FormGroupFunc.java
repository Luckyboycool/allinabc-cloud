package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/24 14:15
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ADM_FORM_GROUP_FUNC",schema = "ADMINDA1")
@NoArgsConstructor
public class FormGroupFunc implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "表单类型")
    @TableField("FORM_TYPE")
    private String formType;

    @ApiModelProperty(value = "群组名称")
    @TableField("GROUP_NAME")
    private String groupName;

    @ApiModelProperty(value = "是否是FAB(Y:是 N:不是)")
    @TableField("IS_FAB")
    private String isFab;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "IS_AVAILABLE")
    private String isAvailable;
}
