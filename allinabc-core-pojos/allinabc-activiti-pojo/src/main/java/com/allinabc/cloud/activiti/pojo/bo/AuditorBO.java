package com.allinabc.cloud.activiti.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/9 14:08
 **/
@Data
@NoArgsConstructor
public class AuditorBO implements Serializable {

    @ApiModelProperty(value = "审批人类型：用户、角色、群组、部门、指定节点审批人的部门主管")
    private String auditorType;

    @ApiModelProperty(value = "类型名称")
    private String auditorTypeName;

    private List<AuditorModel> auditorModels;

}
