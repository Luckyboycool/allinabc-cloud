package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/8 15:29
 **/
@Data
public class NodeDecision implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "节点定义key")
    @TableField("NODE_ID")
    private String nodeId;

    @ApiModelProperty(value = "节点决策")
    @TableField("NODE_DECISION")
    private String nodeDecision;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "流程定义key")
    @TableField("PROCESS_ID")
    private String processId;

    @ApiModelProperty(value = "决策中文名")
    @TableField("DECISION_NAME_CN")
    private String decisionNameCn;

    @ApiModelProperty(value = "决策英文名")
    @TableField("DECISION_NAME_EN")
    private String decisionNameEn;

    @ApiModelProperty(value = "决策英文名")
    @TableField("ORDER_NUM")
    private String orderNum;
}
