package com.allinabc.cloud.activiti.pojo.params;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/8 15:24
 **/
@Data
@TableName(value = "ADM_NODE_DECISION",schema = "ADMINDA1")
public class DecisionParam implements Serializable {

    @ApiModelProperty(value = "节点nodeKey")
    private String nodeId;

    @ApiModelProperty(value = "流程定义key")
    private String processId;

    @ApiModelProperty(value = "当前节点任务ID")
    private String taskId;

    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;
}
