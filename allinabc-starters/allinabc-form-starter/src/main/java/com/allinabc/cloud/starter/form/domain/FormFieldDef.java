package com.allinabc.cloud.starter.form.domain;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:23 下午
 **/
@Data
@TableName(value = "ADM_FORM_FIELD_DEF", schema = "ADMINDA1")
public class FormFieldDef extends BasicInfo implements Serializable {
    @ApiModelProperty(value = "表主键")
    @TableField("TABLE_ID")
    private String tableId;

    @ApiModelProperty(value = "字段名")
    @TableField("FIELD_NAME")
    private String fieldName;

    @ApiModelProperty(value = "字段类型")
    @TableField("FIELD_TYPE")
    private String fieldType;

    @ApiModelProperty(value = "组件类型")
    @TableField("COMPONENT_TYPE")
    private String componentType;

    @ApiModelProperty(value = "类型, 块 section 按钮 function 字段 field")
    @TableField("TYPE")
    private String type;
}
