/*
 * @(#)AuditForm.java 2020年1月9日 上午11:19:51
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.allinabc.cloud.activiti.pojo.params;

import com.allinabc.cloud.activiti.pojo.consts.ActivitiConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 审批表单信息
 */
@Data
public class AuditForm implements Serializable {

    private static final long serialVersionUID = 4220317062676914258L;

    /**
     * 任务ID
     */
    @NotBlank(message = "任务id不能为空")
    private String taskId;
    /**
     * 流程实例ID
     */
    @NotBlank(message = "流程实例id不能为空")
    private String  instanceId;
    /**
     * 审批决策
     */
    @NotBlank(message = "审批决策不能为空")
    private String  decision;
    /**
     * 审批意见
     */
    private String  comment;
    @NotNull(message = "表单主键不能为空")
    private String  businessKey;

}
