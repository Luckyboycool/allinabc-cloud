package com.allinabc.cloud.activiti.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description 节点跳转参数封装类
 * @Author wangtaifeng
 * @Date 2021/4/9 10:49
 **/
@Data
public class NodeJumpParam implements Serializable {

    @ApiModelProperty("实例ID")
    private String procInstId;

    @ApiModelProperty("实例定义Key")
    private String procDefKey;

    @ApiModelProperty("当前任务节点ID")
    private String taskId;

    @ApiModelProperty("当前任务节点定义Key")
    private String taskDefKey;

    @ApiModelProperty("目标节点KEY")
    private String targetNodeKey;

    @ApiModelProperty("表单主键ID")
    private String basicInfoId;

    @ApiModelProperty(value = "指定节点审核人list")
    private List<String> auditors;

    @ApiModelProperty(value = "流程参数")
    private Map<String,Object> variables;
}
