package com.allinabc.cloud.activiti.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/8 18:27
 **/
@Data
public class AssignBO implements Serializable {

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务定义key")
    private String taskDefKey;

    @ApiModelProperty(value = "节点名称")
    private String taskName;

    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;

    @ApiModelProperty(value = "流程定义key")
    private String procDefKey;

    @ApiModelProperty(value = "表单主键ID")
    private String basicInfoId;

    @ApiModelProperty(value = "指定节点审核人list")
    private List<String> auditors;
}
