package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/18 15:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "BIZ_GROUP_AUDITOR")
public class BizGroupAuditor implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableField(value = "ID")
    private String id;

    @ApiModelProperty(value = "节点ID")
    @TableField(value = "NODE_ID")
    private String nodeId;

    @ApiModelProperty(value = "表单类型")
    @TableField(value = "FORM_TYPE")
    private String formType;

    @ApiModelProperty(value = "条件值")
    @TableField(value = "DICT_VALUE")
    private String dictValue;

    @ApiModelProperty(value = "取值值")
    @TableField(value = "GROUP_NAME")
    private String groupName;

    @ApiModelProperty(value = "备注")
    @TableField(value = "REMARK")
    private String remark;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "IS_AVAILABLE")
    private String isAvailable;

}
