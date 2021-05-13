package com.allinabc.cloud.activiti.adaptimpl.decision;

import com.allinabc.cloud.activiti.adapt.DecisionAdapt;
import com.allinabc.cloud.activiti.pojo.consts.DecisionConstant;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.service.BizAuditService;
import com.allinabc.cloud.activiti.service.FormPermissionService;
import com.allinabc.cloud.activiti.service.IProcessInstanceService;
import com.allinabc.cloud.activiti.service.NodeAuditorService;
import com.allinabc.cloud.common.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/7 18:38
 **/
@Service("node_decision_NOAPPLYFLAG")
@Slf4j
public class NoApplyFlagService implements DecisionAdapt {

    @Autowired
    private BizAuditService bizAuditService;

    @Autowired
    private FormPermissionService formPermissionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private NodeAuditorService nodeAuditorService;

    @Autowired
    private IProcessInstanceService processInstanceService;

    @Override
    public List<String> complateTask(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, Task currentTask, User currentUser, String basicInfoId, Map<String, Object> variables) {
        //如果没传的话，直接默认按照同意执行流程
        log.info("没有传值，默认按照同意执行流程");
        bizAuditService.addComment(processInstance, processExecuteParam, DecisionConstant.AGREE, currentTask, null, null,variables);
        log.info("-----");
        formPermissionService.savePermission(false,basicInfoId,currentUser.getAccountType(),currentUser.getId(),processExecuteParam.getVariables(),currentTask.getTaskDefinitionKey(),processInstance.getProcessDefinitionKey(),processInstance.getStartUserId());
        /**
         * 在完成任务之前需要判断是否是加签任务
         */
        DelegationState delegationState = currentTask.getDelegationState();
        if(null != delegationState && delegationState.toString().equals("PENDING")){
            taskService.resolveTask(processExecuteParam.getTaskId(),  processExecuteParam.getVariables());
            log.info("加签任务，不需要设置下一节点审批人");
        }else {
            taskService.complete(processExecuteParam.getTaskId(), processExecuteParam.getVariables());
            List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
            list.forEach(i -> {
                nodeAuditorService.setAuditors(i.getId(), i.getTaskDefinitionKey(), processInstance.getProcessDefinitionKey(), processInstance.getId(), processInstance.getStartUserId(), processExecuteParam.getVariables());
            });
        }
        return processInstanceService.findTaskIdByInstanceId(processExecuteParam.getInstanceId());
    }


}
