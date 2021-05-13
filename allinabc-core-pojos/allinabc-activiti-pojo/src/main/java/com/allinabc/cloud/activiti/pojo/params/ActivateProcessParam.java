package com.allinabc.cloud.activiti.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/9 11:11
 **/
@Data
public class ActivateProcessParam implements Serializable {

    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;

    @ApiModelProperty(value = "流程定义KEY")
    private String procDefKey;

    @ApiModelProperty(value = "业务ID")
    private String basicInfoId;

    @ApiModelProperty(value = "流程参数")
    private Map<String,Object> variables;
}
