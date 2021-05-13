package com.allinabc.cloud.activiti.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description 流程启动参数类
 * @Author wangtaifeng
 * @Date 2021/2/26 14:59
 **/
@Data
@NoArgsConstructor
public class ProcessStartParam implements Serializable {

    private static final long serialVersionUID = -6418292625936453408L;

    @ApiModelProperty(value = "流程定义key")
    private String processDefinitionKey;

    @ApiModelProperty(value = "业务ID")
    private String businessId;

    @ApiModelProperty(value = "流程发起人")
    private String requester;

    @ApiModelProperty(value = "批注")
    private String comment;

    @ApiModelProperty(value = "流程变量参数")
    private Map<String,Object> variables;

    private BasicFormDTO basicFormDTO;

    public ProcessStartParam(String processDefinitionKey,String businessId,String requester,String comment, Map<String,Object> variables,BasicFormDTO basicFormDTO){
        this.processDefinitionKey = processDefinitionKey;
        this.businessId = businessId;
        this.requester = requester;
        this.variables =variables;
        this.basicFormDTO =basicFormDTO;
        this.comment = comment;

    }
}
