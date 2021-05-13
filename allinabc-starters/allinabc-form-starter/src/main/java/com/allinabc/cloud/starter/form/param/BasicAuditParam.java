package com.allinabc.cloud.starter.form.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuditParam implements Serializable {

    @ApiModelProperty(value = "流程ID")
    private String  processId;
    @ApiModelProperty(value = "实例ID")
    private String  instanceId;
    @ApiModelProperty(value = "任务ID")
    private String  taskId;
    @ApiModelProperty(value = "审批决策")
    private String  decision;
    @ApiModelProperty(value = "审批决策人ID")
    private String  auditId;
    @ApiModelProperty(value = "节点key")
    private String taskDefKey;
    @ApiModelProperty(value = "流程审核参数")
    private Map<String,Object> variables;


}
