package com.allinabc.cloud.starter.form.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:29 下午
 **/
@Data
@TableName(value = "ADM_FORM_ACTIVITI_FIELD", schema = "ADMINDA1")
public class FormActivitiField implements Serializable {

    /**
     * FormActivitiDef 的主键
     */
    @ApiModelProperty(value = "节点主键")
    @TableField("ACTIVITI_ID")
    private String activitiId;

    /**
     * FormFieldDef 的主键
     */
    @ApiModelProperty(value = "字段主键")
    @TableField("FIELD_ID")
    private String fieldId;

    @ApiModelProperty(value = "可读可写状态")
    @TableField("RW")
    private String rw;

    @ApiModelProperty(value = "表达式")
    @TableField(value = "EXPRESSION")
    private String expression;
}
