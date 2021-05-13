package com.allinabc.cloud.activiti.adapt;

import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;

import java.util.Map;

/**
 * @Description 节点审批人适配器
 * @Author wangtaifeng
 * @Date 2021/4/6 10:16
 **/
public interface NodeAuditorAdapt {

    void setAuditor(String auditorKey,String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, Map<String, Object> variables);

    void setAuditor(String auditorKey,String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter,NodeJumpParam nodeJumpParam);

}
