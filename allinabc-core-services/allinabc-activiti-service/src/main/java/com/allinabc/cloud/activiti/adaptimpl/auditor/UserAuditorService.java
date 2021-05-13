package com.allinabc.cloud.activiti.adaptimpl.auditor;

import com.allinabc.cloud.activiti.adapt.NodeAuditorAdapt;
import com.allinabc.cloud.activiti.mapper.BizNodeNoAuditorMapper;
import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;
import com.allinabc.cloud.activiti.pojo.po.BizNodeNoAuditor;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 指定用户
 * @Author wangtaifeng
 * @Date 2021/3/22 20:54
 **/
@Service("node_auditor_type_1")
@Slf4j
public class UserAuditorService implements NodeAuditorAdapt {



    @Resource
    private BizNodeNoAuditorMapper bizNodeNoAuditorMapper;

    @Autowired
    private TaskService taskService;

    @Override
    public void setAuditor(String auditorKey, String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, Map<String, Object> variables) {
        log.info("指定用户审批，type=1");
        if(StringUtils.isEmpty(auditorKey)){
            log.error("未找到节点审批人，插入无节点审批人表中");
            User currentUser = CurrentUserUtil.getCurrentUser();
            BizNodeNoAuditor bizNodeNoAuditor = new BizNodeNoAuditor(nodeId, nodeKey, processDefinitionKey, processInsId, starter, currentUser.getId(), new Date());
            if(bizNodeNoAuditorMapper.insert(bizNodeNoAuditor)!=1){
                throw new RuntimeException("节点nodeKey="+nodeKey+" 插入无节点审批人表，入库失败");
            }
        }else {
            //描述：设置节点审批人之前需要判断该节点是否设置过，如果设置过审批人，就不设置
            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(nodeId);
            boolean isDefine = identityLinksForTask.stream().anyMatch(i->auditorKey.equals(i.getUserId()));
            if(isDefine){
                log.info("此节点："+nodeKey+" taskId="+nodeId+" ,已配置过该审核人="+auditorKey+"  ，无需再配置!!!");
            }else {
                log.info("此节点："+nodeKey+"taskId="+nodeId+" ,配置审核人="+auditorKey);
                taskService.addCandidateUser(nodeId, auditorKey);
            }
        }
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
