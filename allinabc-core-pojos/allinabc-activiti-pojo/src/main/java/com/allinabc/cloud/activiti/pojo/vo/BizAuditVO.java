package com.allinabc.cloud.activiti.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/21 16:27
 **/
@Data
public class BizAuditVO implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "任务编号")
    private String taskId;

    @ApiModelProperty(value = "任务定义key")
    private String taskDefKey;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "审核结果（动作）")
    private String action;

    @ApiModelProperty(value = "审核意见")
    private String decision;

    @ApiModelProperty(value = "流程定义key")
    private String procDefKey;

    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;

    @ApiModelProperty(value = "申请人")
    private String applyer;

    @ApiModelProperty(value = "审批人")
    private String auditor;

    @ApiModelProperty(value = "被转办人")
    private String transferredUser;

    @ApiModelProperty(value = "被委托人")
    private String principalUser;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "删除标记（0：未删除默认为'0'）")
    private String flag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTm;
}
