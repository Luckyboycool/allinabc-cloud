package com.allinabc.cloud.activiti.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/8 15:27
 **/
@Data
public class DecisionVO implements Serializable {

    private String id;

    private String nodeId;

    private String nodeDecision;

    private String status;

    private String remark;

    private String processId;

    private String decisionNameCn;

    private String decisionNameEn;

    private String orderNum;

    private String blendDecisionName;
}
