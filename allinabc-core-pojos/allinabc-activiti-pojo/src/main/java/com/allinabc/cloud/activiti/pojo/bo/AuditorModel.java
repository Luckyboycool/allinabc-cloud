package com.allinabc.cloud.activiti.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 18:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditorModel implements Serializable {

    @ApiModelProperty(value = "审批人类型：用户、角色、群组、部门、指定节点审批人的部门主管")
    private String auditorType;

    @ApiModelProperty(value = "类型名称")
    private String auditorTypeName;

    @ApiModelProperty(value = "审批人类型key")
    private String auditorKey;

    @ApiModelProperty(value = "审批人姓名")
    private String auditorName;

    @ApiModelProperty(value = "审批人姓名和工号")
    private String nameAndJobNumber;



}
