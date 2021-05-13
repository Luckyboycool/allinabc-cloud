/*
 * @(#)BizNode.java 2020年1月14日 上午10:59:12
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class BizNode {

    @TableId(value = "id", type = IdType.AUTO)
    private Long    id;
    /** 节点ID*/
    private String  nodeId;
    /** 类型 1：角色 2：部门负责人 3：用户 4：所属部门负责人*/
    private Integer type;
    /** 类型对应负责人的值*/
    private String  auditor;

}
