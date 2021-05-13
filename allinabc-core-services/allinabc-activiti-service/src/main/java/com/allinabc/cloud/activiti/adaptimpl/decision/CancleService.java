package com.allinabc.cloud.activiti.adaptimpl.decision;

import com.allinabc.cloud.activiti.adapt.DecisionAdapt;
import com.allinabc.cloud.activiti.pojo.consts.DecisionConstant;
import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.service.BizAuditService;
import com.allinabc.cloud.activiti.service.FormPermissionService;
import com.allinabc.cloud.activiti.service.impl.GtaAsyncTask;
import com.allinabc.cloud.common.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/7 18:12
 **/
@Service("node_decision_C")
@Slf4j
public class CancleService implements DecisionAdapt {

    @Autowired
    private BizAuditService bizAuditService;

    @Autowired
    private FormPermissionService formPermissionService;

    @Autowired
    private GtaAsyncTask gtaAsyncTask;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> complateTask(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, Task currentTask, User currentUser, String basicInfoId, Map<String, Object> variables) {
            //取消
            log.info("取消");
            TaskParam taskParam = new TaskParam();
            taskParam.setProcessDefKey(processInstance.getProcessDefinitionKey());
            taskParam.setProcessInstId(processInstance.getId());
            taskParam.setReason(processExecuteParam.getComment());
            taskParam.setBasicInfoId(processExecuteParam.getBasicInfoId());
            bizAuditService.addComment(processInstance, processExecuteParam, DecisionConstant.CANCLE, currentTask, null, null,variables);

            formPermissionService.savePermission(false, basicInfoId, currentUser.getAccountType(), currentUser.getId(), processExecuteParam.getVariables(), currentTask.getTaskDefinitionKey(), processInstance.getProcessDefinitionKey(),processInstance.getStartUserId());

            gtaAsyncTask.cancleTask(taskParam);
            return null;

    }
}
