package com.allinabc.cloud.activiti.adaptimpl.decision;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.DecisionAdapt;
import com.allinabc.cloud.activiti.pojo.consts.DecisionConstant;
import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.service.BizAuditService;
import com.allinabc.cloud.activiti.service.FormPermissionService;
import com.allinabc.cloud.activiti.service.IProcessInstanceService;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/7 18:27
 **/
@Service("node_decision_E")
@Slf4j
public class EntrustService implements DecisionAdapt {

    @Autowired
    private BizAuditService bizAuditService;

    @Autowired
    private FormPermissionService formPermissionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IProcessInstanceService processInstanceService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> complateTask(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, Task currentTask, User currentUser, String basicInfoId, Map<String, Object> variables) {
        log.info("委托");
        TaskParam taskParam = new TaskParam();
        taskParam.setTaskId(processExecuteParam.getTaskId());
        //被转办人ID
        String delegateUserId = (String) variables.get("delegateUserId");
        if(StringUtils.isEmpty(delegateUserId)){
            throw new RuntimeException("委托人ID为空");
        }
        taskParam.setDelegateUserId(delegateUserId);
        //设置操作人ID
        taskParam.setUserId(processExecuteParam.getUserId());
        bizAuditService.addComment(processInstance, processExecuteParam, DecisionConstant.ENTRUST, currentTask, null, delegateUserId,variables);
        formPermissionService.savePermission(false,basicInfoId,currentUser.getAccountType(),currentUser.getId(),processExecuteParam.getVariables(),currentTask.getTaskDefinitionKey(),processInstance.getProcessDefinitionKey(),processInstance.getStartUserId());
        delegateTask(taskParam);
        return processInstanceService.findTaskIdByInstanceId(processExecuteParam.getInstanceId());
    }




    /**
     * 委托任务
     *
     * @param taskParam
     */
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(TaskParam taskParam) {
        log.info("委托任务参数=" + JSON.toJSONString(taskParam));
        if (StringUtils.isEmpty(taskParam.getDelegateUserId())) {
            throw new RuntimeException("被委托人ID不能为空");
        }
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskParam.getTaskId());
        identityLinksForTask.forEach(j->{
            if(!StringUtils.isEmpty(j.getUserId())) {
                if(!j.getUserId().equals(taskParam.getUserId())) {
                    taskService.deleteCandidateUser(taskParam.getTaskId(), j.getUserId());
                }
            }
        });
        taskService.delegateTask(taskParam.getTaskId(), taskParam.getDelegateUserId());
        log.info("委派成功");
    }
}
