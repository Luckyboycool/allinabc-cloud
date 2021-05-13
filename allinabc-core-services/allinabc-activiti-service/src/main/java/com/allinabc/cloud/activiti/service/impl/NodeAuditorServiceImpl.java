package com.allinabc.cloud.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.NodeAuditorAdapt;
import com.allinabc.cloud.activiti.config.NodeAuditorManager;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;
import com.allinabc.cloud.activiti.pojo.po.NodeAuditor;
import com.allinabc.cloud.activiti.service.NodeAuditorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/6 10:29
 **/
@Service
@Slf4j
public class NodeAuditorServiceImpl implements NodeAuditorService {


    @Resource
    private NodeAuditorMapper nodeAuditorMapper;

//    @Autowired
//    private ApplyerAuditorService applyerAuditorService;
//
//    @Autowired
//    private ApplyerLeaderAuditorService applyerLeaderAuditorService;
//
//    @Autowired
//    private DeptmentAuditorService deptmentAuditorService;
//
//    @Autowired
//    private FabGroupAuditorService fabGroupAuditorService;
//
//    @Autowired
//    private GroupAuditorService groupAuditorService;
//
//    @Autowired
//    private NodeAuditorLeaderAuditorService nodeAuditorLeaderAuditorService;
//
//    @Autowired
//    private RoleAuditorService roleAuditorService;
//
//    @Autowired
//    private UserAuditorService userAuditorService;

    @Autowired
    private NodeAuditorManager nodeAuditorManager;


    /**
     * 设置节点审批人
     * @param nodeId
     * @param nodeKey
     * @param processDefinitionKey
     * @param processInsId
     * @param starter
     * @param variables
     */
    @Override
    public void setAuditors(String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, Map<String, Object> variables) {
        //首先查询配置人
        List<NodeAuditor> nodeTypeList = nodeAuditorMapper.selectNodeAuditTypeByNodeIdAndProcessId(nodeKey, processDefinitionKey);
        log.info("节点配置的审批人类型="+ JSON.toJSONString(nodeTypeList));
        nodeTypeList.forEach(i -> {
            NodeAuditorAdapt nodeAuditorAdapt =null;
            nodeAuditorAdapt = nodeAuditorManager.getNodeAuditorService("node_auditor_type_"+ i.getAuditorType());
            if(nodeAuditorAdapt!=null) {
                nodeAuditorAdapt.setAuditor(i.getAuditor(), nodeId, nodeKey, processDefinitionKey, processInsId, starter, variables);
            }else{
                log.error("未找到节点实现类！！！！！！！！！！！！！！！！！！！！！！！");
            }
        });
    }

    @Override
    public void setAuditors(String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, NodeJumpParam nodeJumpParam) {
        //首先查询配置人
        List<NodeAuditor> nodeTypeList = nodeAuditorMapper.selectNodeAuditTypeByNodeIdAndProcessId(nodeKey, processDefinitionKey);
        log.info("admin节点配置的审批人类型="+ JSON.toJSONString(nodeTypeList));
        nodeTypeList.forEach(i -> {
            NodeAuditorAdapt nodeAuditorAdapt =null;
            nodeAuditorAdapt = nodeAuditorManager.getNodeAuditorService("node_auditor_type_"+ i.getAuditorType());
            if(nodeAuditorAdapt!=null) {
                nodeAuditorAdapt.setAuditor(i.getAuditor(), nodeId, nodeKey, processDefinitionKey, processInsId, starter, nodeJumpParam);
            }else{
                log.error("未找到节点实现类！！！！！！！！！！！！！！！！！！！！！！！");
            }
        });
    }




}
