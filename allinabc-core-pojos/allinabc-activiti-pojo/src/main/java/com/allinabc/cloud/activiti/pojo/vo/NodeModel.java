package com.allinabc.cloud.activiti.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/1 14:49
 **/
@Data
public class NodeModel implements Serializable {

    private static final long serialVersionUID = -3488104476786053652L;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点的key
     */
    private String nodeId;

    /**
     * 节点类型
     */
    private Integer nodeType;

}
