package com.allinabc.cloud.org.pojo.po;

import com.allinabc.cloud.common.core.domain.Organization;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员实体", description = "")
@TableName(value = "ORG_DEPARTMENT", schema = "")
public class Department extends Organization implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 英文
     */
    private String nameEn;
    /**
     * 组织编码
     */
    private String code;
    /**
     * 成本中心代码
     */
    private String costCenterCode;
    /**
     * 组织等级
     */
    private Integer deptLevel;
    /**
     * 上级ID
     */
    private String pid;
    /**
     * 描述
     */
    private String director;
    /**
     * 主管
     */
    private String assistant;
    /**
     * AD群组
     */
    private String adGroupName;
    /**
     * 是否无效（N/Y)
     */
    private String isInvalid;
    /**
     * 是否隐藏 （N/Y)
     */
    private String isHidden;
    /**
     * 当前组织编码（包含上级）
     */
    private String codeCurrent;
    /**
     * 中文显示名称
     */
    private String displayname;
    /**
     * 英文显示名称
     */
    private String pinyin;
    /**
     * 失效日期
     */
    private Date disableDate;
    /**
     * 邮件
     */
    private String email;

    /**
     * 简称
     */
    @TableField(value = "DEPT_MASK")
    private String deptMask;

    /**
     * 嘉扬对应的depid
     */
    private String departmentCode;

    /**
     * canceled：1：空值，2：NULL ,3: 1
     * canceled值为空或NULL时，表明部门有效
     */
    private Integer canceled;

}
