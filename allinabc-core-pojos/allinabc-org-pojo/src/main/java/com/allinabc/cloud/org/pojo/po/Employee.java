package com.allinabc.cloud.org.pojo.po;

import com.allinabc.cloud.common.core.domain.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "人员实体", description = "")
@TableName(value = "ORG_EMPLOYEE")
public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String secLevel;
    /**
     * 所属组织
     */
    @ApiModelProperty(value = "所属部门")
    private String deptId;

    /**
     * 座机
     */
    @ApiModelProperty(value = "座机")
    private String telephone;

    /**
     * 工号
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


    @ApiModelProperty(value = "账号")
    private String loginId;

    /**
     * -- 忽略字段 --
     */
    @TableField(exist = false)
    private String accountType;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String remark;
    @TableField(exist = false)
    private String createdBy;

    @TableField(exist = false)
    private Date createTm;
    @TableField(exist = false)
    private String updatedBy;

    @TableField(exist = false)
    private Date updateTm;

}
