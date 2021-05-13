package com.allinabc.cloud.activiti.adaptimpl.auditor;

import com.allinabc.cloud.activiti.adapt.NodeAuditorAdapt;
import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 设置申请人为当前节点审批人(节点类型=7)
 * @Author wangtaifeng
 * @Date 2021/3/22 19:05
 **/
@Service("node_auditor_type_7")
@Slf4j
public class ApplyerAuditorService implements NodeAuditorAdapt {

    @Autowired
    private TaskService taskService;

    @Override
    public void setAuditor(String auditorKey,String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, Map<String, Object> variables) {
        log.info("节点类型=【7】;获取的申请人starter="+starter);
        if(StringUtils.isEmpty(starter)){
            throw new RuntimeException("申请人信息为空");
        }
        //描述：设置节点审批人之前需要判断该节点是否设置过，如果设置过审批人，就不设置
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(nodeId);
        boolean isDefine = identityLinksForTask.stream().anyMatch(i->starter.equals(i.getUserId()));
        if(isDefine){
            log.info("此节点："+nodeKey+" taskId="+nodeId+" ,已配置过该审核人="+starter+"  ，无需再配置!!!");
        }else {
            log.info("此节点："+nodeKey+"taskId="+nodeId+" ,配置审核人"+starter);
            taskService.addCandidateUser(nodeId, starter);
        }
        log.info("设置节点nodeKey="+nodeKey+"成功");
    }

    /**
     * admin设置节点审核人
     * @param auditorKey
     * @param nodeId
     * @param nodeKey
     * @param processDefinitionKey
     * @param processInsId
     * @param starter
     */
    @Override
    public void setAuditor(String auditorKey, String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, NodeJumpParam nodeJumpParam) {
        this.setAuditor(auditorKey,nodeId,nodeKey,processDefinitionKey,processInsId,starter,new ConcurrentHashMap<>());
    }
}
