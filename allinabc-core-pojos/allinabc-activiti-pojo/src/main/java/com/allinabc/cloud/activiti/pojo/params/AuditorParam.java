package com.allinabc.cloud.activiti.pojo.params;

import com.allinabc.cloud.activiti.pojo.bo.AuditorModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 19:27
 **/
@Data
public class AuditorParam implements Serializable {

    private static final long serialVersionUID = -7558734031550786102L;

    @ApiModelProperty(value = "节点ID")
    private String nodeId;

    @ApiModelProperty(value = "流程定义ID")
    private String processId;

    @ApiModelProperty(value = "审批人类型集合")
    private List<AuditorModel> auditorModels;
}
