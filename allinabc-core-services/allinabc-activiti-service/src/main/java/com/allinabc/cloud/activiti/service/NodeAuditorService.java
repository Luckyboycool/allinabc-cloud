package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;

import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/6 10:29
 **/
public interface NodeAuditorService {

    /**
     * 设置节点审批人
     * @param nodeId
     * @param nodeKey
     * @param processDefinitionKey
     * @param processInsId
     * @param starter
     * @param variables
     */
    void setAuditors(String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, Map<String, Object> variables);


    /**
     * admin设置节点审批人
     * @param nodeId
     * @param nodeKey
     * @param processDefinitionKey
     * @param processInsId
     * @param starter
     */
    void setAuditors(String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, NodeJumpParam nodeJumpParam);

}
