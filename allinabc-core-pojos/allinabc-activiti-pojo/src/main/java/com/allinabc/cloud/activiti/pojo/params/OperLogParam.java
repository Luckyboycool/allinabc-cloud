package com.allinabc.cloud.activiti.pojo.params;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/5/9 16:21
 **/
@Data
public class OperLogParam implements Serializable {

    @ApiModelProperty(value = "流程定义KEY")
    private String procDefKey;

    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;

    @ApiModelProperty(value = "任务定义key")
    private String taskDefKey;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "表单主键")
    private String basicInfoId;

    @ApiModelProperty(value = "操作类型")
    private String action;

    @ApiModelProperty(value = "目标节点key")
    private String targetTaskDefKey;

    @ApiModelProperty(value = "指定节点处理人")
    private String auditors;

    @ApiModelProperty(value = "重启流程实例ID")
    private String newProcInstId;

    @ApiModelProperty(value = "备注")
    private String remark;

}
