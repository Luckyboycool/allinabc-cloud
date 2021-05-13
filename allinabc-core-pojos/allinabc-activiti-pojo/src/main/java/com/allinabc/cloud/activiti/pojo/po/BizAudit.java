
package com.allinabc.cloud.activiti.pojo.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "BIZ_AUDIT")
public class BizAudit implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "任务编号")
    @TableField("TASK_ID")
    private String taskId;

    @ApiModelProperty(value = "任务定义key")
    @TableField("TASK_DEF_KEY")
    private String taskDefKey;

    @ApiModelProperty(value = "审核结果（动作）")
    @TableField("ACTION")
    private String action;

    @ApiModelProperty(value = "审核意见")
    @TableField("DECISION")
    private String decision;

    @ApiModelProperty(value = "流程定义key")
    @TableField("PROC_DEF_KEY")
    private String procDefKey;

    @ApiModelProperty(value = "流程实例ID")
    @TableField("PROC_INST_ID")
    private String procInstId;

    @ApiModelProperty(value = "申请人")
    @TableField("APPLYER")
    private String applyer;

    @ApiModelProperty(value = "审批人")
    @TableField("AUDITOR")
    private String auditor;

    @ApiModelProperty(value = "被转办人")
    @TableField("TRANSFERRED_USER")
    private String transferredUser;

    @ApiModelProperty(value = "被委托人")
    @TableField("PRINCIPAL_USER")
    private String principalUser;

    @ApiModelProperty(value = "业务表单主键")
    @TableField("BASIC_INFO_ID")
    private String basicInfoId;

    @ApiModelProperty(value = "用户类型")
    @TableField("USER_TYPE")
    private String userType;

    @ApiModelProperty(value = "删除标记（0：未删除默认为'0'）")
    @TableField("FLAG")
    private String flag;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TM")
    private Date createTm;




}
