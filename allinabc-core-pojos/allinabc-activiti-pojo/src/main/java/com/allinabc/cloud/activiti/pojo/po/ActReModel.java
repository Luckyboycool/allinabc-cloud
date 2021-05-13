package com.allinabc.cloud.activiti.pojo.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义表-模型信息 ACT_RE_MODEL
 */
@Data
@TableName(value = "ACT_RE_MODEL")
@EqualsAndHashCode(callSuper = false)
public class ActReModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "ID_", type = IdType.AUTO)
    private String            id;

    /** 乐观锁 */
    @TableField(value = "REV_")
    private Long              rev;

    /** 模型名称 */
    @Excel(name = "名称")
    @TableField(value = "NAME_")
    private String            name;

    /** 模型标识 */
    @Excel(name = "模型标识")
    @TableField(value = "KEY_")
    private String            key;

    /** 类型，用户自己对流程模型的分类。 */
    @Excel(name = "分类")
    @TableField(value = "CATEGORY_")
    private String            category;

    /** 创建时间 */
    @TableField(value = "CREATE_TIME_")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date              createTime;

    /** 最新修改时间 */
    @TableField(value = "LAST_UPDATE_TIME_")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date              lastUpdateTime;

    /** 版本,从1开始 */
    @Excel(name = "版本")
    @TableField(value = "VERSION_")
    private Long              version;

    /** 以json格式保存流程定义的信息 */
    @TableField(value = "META_INFO_")
    private String            metaInfo;

    /** 部署ID */
    @Excel(name = "部署ID")
    @TableField(value = "DEPLOYMENT_ID_")
    private String            deploymentId;

    /** 编辑源值ID */
    @TableField(value = "EDITOR_SOURCE_VALUE_ID_")
    private String            editorSourceValueId;

    /** 编辑源额外值ID */
    @TableField(value = "EDITOR_SOURCE_EXTRA_VALUE_ID_")
    private String            editorSourceExtraValueId;

    /** 租户 */
    @TableField(value = "TENANT_ID_")
    private String            tenantId;

}
