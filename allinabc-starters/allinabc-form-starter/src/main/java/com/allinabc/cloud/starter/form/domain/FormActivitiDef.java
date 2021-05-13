package com.allinabc.cloud.starter.form.domain;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:26 下午
 **/
@Data
@TableName(value = "ADM_FORM_ACTIVITI_DEF", schema = "ADMINDA1")
public class FormActivitiDef extends BasicInfo implements Serializable {

    @ApiModelProperty(value = "表单类型")
    @TableField("FORM_TYPE")
    private String formType;

    @ApiModelProperty(value = "节点键")
    @TableField("NODE_KEY")
    private String NodeKey;

    @ApiModelProperty(value = "节点名")
    @TableField("NODE_NAME")
    private String NodeName;
}
