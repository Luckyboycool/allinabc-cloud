package com.allinabc.cloud.activiti.adaptimpl.auditor;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.NodeAuditorAdapt;
import com.allinabc.cloud.activiti.mapper.BizNodeNoAuditorMapper;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
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
import java.util.stream.Collectors;

/**
 * @Description 角色审核人 type=2
 * @Author wangtaifeng
 * @Date 2021/3/22 19:05
 **/
@Service("node_auditor_type_2")
@Slf4j
public class RoleAuditorService implements NodeAuditorAdapt {

    @Resource
    private NodeAuditorMapper nodeAuditorMapper;

    @Resource
    private BizNodeNoAuditorMapper bizNodeNoAuditorMapper;

    @Autowired
    private TaskService taskService;

    @Override
    public void setAuditor(String auditorKey, String nodeId, String nodeKey, String processDefinitionKey, String processInsId, String starter, Map<String, Object> variables) {
        log.info("指定角色审核人;类型=【2】角色名称="+auditorKey);
        List<String> roleIds = nodeAuditorMapper.selectRoleAuditorByRoleId(auditorKey);
        roleIds = roleIds.stream().filter(s-> !StringUtils.isEmpty(s)).collect(Collectors.toList());

        log.info("下一节点的审批人为" + JSON.toJSONString(roleIds));

        //描述：设置节点审批人之前需要判断该节点是否设置过，如果设置过审批人，就不设置
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(nodeId);

        if (!CollUtil.isEmpty(roleIds)) {
            log.info("设置节点nodeKey= "+nodeKey+"  审批人");
            roleIds.stream().distinct().forEach(id -> {
                boolean isDefine = identityLinksForTask.stream().anyMatch(i->id.equals(i.getUserId()));
                if(isDefine){
                    log.info("此节点："+nodeKey+" taskId="+nodeId+" ,已配置过该审核人="+id+"  ，无需再配置!!!");
                }else {
                    log.info("此节点："+nodeKey+"taskId="+nodeId+" ,配置审核人="+id);
                    taskService.addCandidateUser(nodeId, id);
                }
            });
        } else {
            log.error("未找到节点审批人，插入无节点审批人表中");
            User currentUser = CurrentUserUtil.getCurrentUser();
            BizNodeNoAuditor bizNodeNoAuditor = new BizNodeNoAuditor(nodeId, nodeKey, processDefinitionKey, processInsId, starter, currentUser.getId(), new Date());
            if(bizNodeNoAuditorMapper.insert(bizNodeNoAuditor)!=1){
                throw new RuntimeException("节点nodeKey="+nodeKey+" 插入无节点审批人表，入库失败");
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
