package com.allinabc.cloud.activiti.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/1 20:39
 **/
@Data
@NoArgsConstructor
public class ProcessExecuteParam implements Serializable {

    @ApiModelProperty(value = "流程实例ID")
    private String instanceId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "批注")
    private String comment;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "表单id")
    private String basicInfoId;

    @ApiModelProperty(value = "流程变量参数")
    private Map<String,Object> variables;

    private BasicFormDTO basicFormDTO;

    public ProcessExecuteParam(String instanceId,String taskId,String comment,String userId,String basicInfoId,Map<String,Object> variables,BasicFormDTO basicFormDTO){
        this.instanceId = instanceId;
        this.comment = comment;
        this.variables =variables;
        this.userId = userId;
        this.basicInfoId = basicInfoId;
        this.taskId =taskId;
        this.basicFormDTO = basicFormDTO;
    }

}
