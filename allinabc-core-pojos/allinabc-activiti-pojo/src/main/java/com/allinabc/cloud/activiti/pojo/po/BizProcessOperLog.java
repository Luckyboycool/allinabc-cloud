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
 * @Date 2021/4/22 17:37
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "BIZ_PROCESS_OPER_LOG",schema = "ACTDA1")
@NoArgsConstructor
public class BizProcessOperLog extends BasicInfo {

    @ApiModelProperty(value = "流程定义KEY")
    @TableField("PROC_DEF_KEY")
    private String procDefKey;

    @ApiModelProperty(value = "流程实例ID")
    @TableField("PROC_INST_ID")
    private String procInstId;

    @ApiModelProperty(value = "任务定义key")
    @TableField("TASK_DEF_KEY")
    private String taskDefKey;

    @ApiModelProperty(value = "任务ID")
    @TableField("TASK_ID")
    private String taskId;

    @ApiModelProperty(value = "表单主键")
    @TableField("BASIC_INFO_ID")
    private String basicInfoId;

    @ApiModelProperty(value = "操作类型")
    @TableField("ACTION")
    private String action;

    @ApiModelProperty(value = "目标节点key")
    @TableField("TARGET_TASK_DEF_KEY")
    private String targetTaskDefKey;

    @ApiModelProperty(value = "指定节点处理人")
    @TableField("AUDITORS")
    private String auditors;

    @ApiModelProperty(value = "重启流程实例ID")
    @TableField("NEW_PROC_INST_ID")
    private String newProcInstId;
}
