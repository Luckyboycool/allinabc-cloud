package com.allinabc.cloud.admin.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/8 2:11 下午
 **/
@Data
public class EmployeeDetailVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private String Id;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String  name;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String jobNumber;

    /**
     * 岗位（人员）等级
     */
    @ApiModelProperty(value = "岗位（人员）等级")
    private String SecLevel;
    /**
     * 所属组织
     */
    @ApiModelProperty(value = "所属组织")
    private String deptId;

    /**
     * 座机
     */
    @ApiModelProperty(value = "座机")
     private String telephone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 直接主管
     */
    @ApiModelProperty(value = "直接主管")
    private String directSupervisor;

    /**
     * 上级组织
     */
    @ApiModelProperty(value = "上级组织")
    private String partDeptId;
    /**
     * 员工账号状态
     * 1 在职  4 离职
     */
    @ApiModelProperty(value = "员工账号状态 1 在职  4 离职")
    private Integer status;


}
