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
 * @Description 转办
 * @Author wangtaifeng
 * @Date 2021/4/7 18:22
 **/
@Service("node_decision_T")
@Slf4j
public class TransferService implements DecisionAdapt {

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
    public List<String> complateTask(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, Task currentTask, User currentUser, String basicInfoId, Map<String, Object>variables) {
        log.info("转办");
        TaskParam taskParam = new TaskParam();
        taskParam.setTaskId(processExecuteParam.getTaskId());
        //转办人ID
        taskParam.setUserId(processExecuteParam.getUserId());
        //被转办人ID
        String transUserId = (String) variables.get("transUserId");
        if(StringUtils.isEmpty(transUserId)){
            throw new RuntimeException("转办人ID为空");
        }
        taskParam.setTransUserId(transUserId);
        bizAuditService.addComment(processInstance, processExecuteParam, DecisionConstant.TRANSFER, currentTask, transUserId, null,variables);
        formPermissionService.savePermission(false,basicInfoId,currentUser.getAccountType(),currentUser.getId(),processExecuteParam.getVariables(),currentTask.getTaskDefinitionKey(),processInstance.getProcessDefinitionKey(),processInstance.getStartUserId());
        transferTask(taskParam);
        return processInstanceService.findTaskIdByInstanceId(processExecuteParam.getInstanceId());
    }


    /**
     * 转办任务
     *
     * @param taskParam
     */
    @Transactional(rollbackFor = Exception.class)
    public void transferTask(TaskParam taskParam) {
        log.info("转办任务参数=" + JSON.toJSONString(taskParam));
        if (StringUtils.isEmpty(taskParam.getTransUserId())) {
            throw new RuntimeException("被转办人ID不能为空");
        }
        //描述：transUserId可能为一个集合，用，隔开所以需要先分割
        String[] transUserIds = taskParam.getTransUserId().split(",");
        //首先删除之前人的userId
        //根据taskId查询当前节点所有审批人,然后删除所有
        //todo
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskParam.getTaskId());
        identityLinksForTask.forEach(j->{
            if(!StringUtils.isEmpty(j.getUserId())) {
                taskService.deleteCandidateUser(taskParam.getTaskId(), j.getUserId());
            }
        });
        //taskService.deleteCandidateUser(taskParam.getTaskId(), taskParam.getUserId());
        for (int i = 0; i <transUserIds.length ; i++) {
            log.info("任务Id="+taskParam.getTaskId()+"被转办人Id="+transUserIds[i]);
            taskService.addCandidateUser(taskParam.getTaskId(), transUserIds[i]);
        }

        //如果要查询转给他人处理的任务，可以同时将OWNER进行设置：
        //taskService.setOwner(taskParam.getTaskId(),taskParam.getUserId());
        log.info("转办成功");
    }
}
