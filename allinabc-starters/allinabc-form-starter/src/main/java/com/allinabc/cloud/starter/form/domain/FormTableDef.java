package com.allinabc.cloud.starter.form.domain;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:21 下午
 **/
@Data
@TableName(value = "ADM_FORM_TABLE_DEF", schema = "ADMINDA1")
public class FormTableDef extends BasicInfo implements Serializable {

    /**
     * 模块名
     */
    @ApiModelProperty(value = "表单类型")
    @TableField("FORM_TYPE")
    private String formType;

    /**
     * 多个子表单
     */
    @ApiModelProperty(value = "业务对象名")
    @TableField("ENTITY_KEY")
    private String entityKey;
}
