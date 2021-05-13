/*
 * @(#)ActReProcdef.java 2020年1月3日 下午6:19:47
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 流程定义-解析表 ACT_RE_PROCDEF
 * 流程解析表，解析成功了，在该表保存一条记录。业务流程定义数据表
 */
@Data
@Accessors(chain = true)
@TableName(value = "ACT_RE_PROCDEF")
public class ActReProcdef implements Serializable {

    /** 流程ID，由“流程编号：流程版本号：自增长ID”组成 */
    @TableId(value = "ID_", type = IdType.AUTO)
    private String  id;

    /** 版本号, 乐观锁 */
    @TableField(value = "REV_")
    private String  rev;

    /** 流程命名空间（该编号就是流程文件targetNamespace的属性值） */
    @TableField(value = "CATEGORY_")
    private String  category;

    /** 流程名称（该编号就是流程文件process元素的name属性值） */
    @TableField(value = "NAME_")
    private String  name;

    /** 流程编号(该编号就是流程文件process元素的id属性值) */
    @TableField(value = "KEY_")
    private String  key;

    /** 流程版本号（由程序控制，新增即为1，修改后依次加1来完成的） */
    @TableField(value = "VERSION_")
    private String  version;

    /** 部署编号 部署表ID*/
    @TableField(value = "DEPLOYMENT_ID_")
    private String  deploymentId;

    /** 资源文件名称 流程bpmn文件名称*/
    @TableField(value = "RESOURCE_NAME_")
    private String  resourceName;

    /** 图片资源文件名称 png流程图片名称*/
    @TableField(value = "DGRM_RESOURCE_NAME_")
    private String  dgrmResourceName;

    /** 描述信息 */
    @TableField(value = "DESCRIPTION_")
    private String  description;

    /** 是否从key启动 start节点是否存在formKey 0否  1是 */
    @TableField(value = "HAS_START_FORM_KEY_")
    private Boolean hasStartFormKey;

    /** 是否挂起 1激活 2挂起 */
    @TableField(value = "SUSPENSION_STATE_")
    private Integer  suspensionState;

}
