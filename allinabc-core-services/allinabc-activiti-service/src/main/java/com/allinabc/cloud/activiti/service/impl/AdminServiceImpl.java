package com.allinabc.cloud.activiti.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.config.JumpAnyWhereCmd;
import com.allinabc.cloud.activiti.mapper.BizAuditMapper;
import com.allinabc.cloud.activiti.mapper.BizProcessOperLogMapper;
import com.allinabc.cloud.activiti.pojo.bo.AssignBO;
import com.allinabc.cloud.activiti.pojo.consts.OperLogConstant;
import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ActivateProcessParam;
import com.allinabc.cloud.activiti.pojo.params.AssignParam;
import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;
import com.allinabc.cloud.activiti.pojo.po.BizAudit;
import com.allinabc.cloud.activiti.pojo.po.BizProcessOperLog;
import com.allinabc.cloud.activiti.service.AdminService;
import com.allinabc.cloud.activiti.service.NodeAuditorService;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/9 10:45
 **/
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Resource
    private BizAuditMapper bizAuditMapper;

    @Autowired
    private IdentityService identityService;

    @Resource
    private BasicFormMapper basicFormMapper;

    @Resource
    private BizProcessOperLogMapper bizProcessOperLogMapper;

    @Autowired
    private GtaAsyncTask gtaAsyncTask;

    @Autowired
    private NodeAuditorService nodeAuditorService;

    /**
     * 给节点设置审批人(主要为未设置节点审批人的节点设置审批人)
     *
     * @param assignParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignNodeAuditor(AssignParam assignParam) {
        log.info("指派人接口请求参数=" + JSON.toJSONString(assignParam));
        List<AssignBO> assignList = assignParam.getAssignList();
        assignList.forEach(i -> {
            List<String> auditors = i.getAuditors();
            auditors = auditors.stream().filter(s -> s != null).collect(Collectors.toList());
            //首先判断之前该节点是否有审核人
            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(i.getTaskId());
            if (!CollUtil.isEmpty(identityLinksForTask)) {
                identityLinksForTask.forEach(m -> {
                    taskService.deleteCandidateUser(i.getTaskId(), m.getUserId());
                });
            }
            auditors.forEach(j -> {
                taskService.addCandidateUser(i.getTaskId(), j);
            });

            String auditorIds = StringUtils.join(auditors.toArray(), ",");
            log.info("管理员指派操作");
            String remark = StrUtil.format(OperLogConstant.REASSIGN_REMARK, i.getBasicInfoId(), i.getTaskName(), auditorIds);
            log.info(remark);
            saveBizProcessOperLog(OperLogConstant.REASSIGN, remark, i.getBasicInfoId(), i.getProcDefKey(), i.getProcInstId(), i.getTaskDefKey(), i.getTaskId(), null, auditorIds, null);

        });
        log.info("指派结束");
    }


    /**
     * 指定跳转到目标节点
     *
     * @param nodeJumpParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void nodeJump(NodeJumpParam nodeJumpParam) {
        log.info("跳转到目标节点请求参数=" + JSON.toJSONString(nodeJumpParam));
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(nodeJumpParam.getProcInstId()).singleResult();
        if (processInstance == null) {
            throw new BusinessException("该流程已结束不可设置跳转到指定节点");
        }
        log.info("开始设置跳转到目标节点...");
        processEngine.getManagementService().executeCommand(new JumpAnyWhereCmd(nodeJumpParam.getTaskId(), nodeJumpParam.getTargetNodeKey()));
        log.info("设置节点跳转结束。。开始设置节点审批人");
        Task task = taskService.createTaskQuery().processInstanceId(nodeJumpParam.getProcInstId()).taskDefinitionKey(nodeJumpParam.getTargetNodeKey()).singleResult();
        nodeAuditorService.setAuditors(task.getId(), task.getTaskDefinitionKey(), nodeJumpParam.getProcDefKey(), nodeJumpParam.getProcInstId(), processInstance.getStartUserId(), nodeJumpParam);
        //todo记录日志
        saveBizProcessOperLog(OperLogConstant.GRAP, OperLogConstant.GRAP_REMARK, nodeJumpParam.getBasicInfoId(), nodeJumpParam.getProcDefKey(), nodeJumpParam.getProcInstId(), nodeJumpParam.getTaskDefKey(), nodeJumpParam.getTaskId(), nodeJumpParam.getTargetNodeKey(), null, null);

        log.info("跳转结束");
    }


    /**
     * 重新启动一个流程(流程死掉此种情况，重新根据审批历史将新流程设置到之前流转的节点)
     *
     * @param activateProcessParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restartNewProcessInst(ActivateProcessParam activateProcessParam) {
        //1.重启流程，然后设置节点到当前审批记录的下一个节点
        //2.修改业务表中的流程ID
        //3.记录日志
        List<BizAudit> bizAuditList = bizAuditMapper.selectBizAuditByBasicInfoIdAndProcessInstId(activateProcessParam.getBasicInfoId(), activateProcessParam.getProcInstId());
        if (CollUtil.isEmpty(bizAuditList)) {
            throw new BusinessException("审核记录为空");
        }
        //设置流程发起人
        identityService.setAuthenticatedUserId(bizAuditList.get(0).getApplyer());

        log.info("开始重新启动一个流程来代替之前死掉的流程");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(activateProcessParam.getProcDefKey(), activateProcessParam.getBasicInfoId(), activateProcessParam.getVariables());


//        bizAuditList.forEach(a->{
//            //遍历执行审核记录
//            switch (a.getAction()){
//                //查询出一个任务集合，判断该审核记录是否是此任务集合中的哪一个
//                case "submit":
//                    log.info("提交 设置下一节点");
//                    HashMap<String, Object> hs = Maps.newHashMap();
//                    hs.put("applyFlag","Y");
//                    List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//                    list.forEach(t->{
//                        if(t.getTaskDefinitionKey().equals(a.getTaskDefKey())){
//                            //完成任务之前先设置任务审核人
//                            taskService.addCandidateUser(t.getId(),a.getAuditor());
//                            taskService.complete(t.getId(),hs);
//                        }
//                    });
//                    break;
//                case "Y":
//                    log.info("同意  设置下一节点");
//                    HashMap<String, Object> hs2 = Maps.newHashMap();
//                    hs2.put("applyFlag","Y");
//                    List<Task> list2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//                    list2.forEach(t->{
//                        if(t.getTaskDefinitionKey().equals(a.getTaskDefKey())){
//                            taskService.addCandidateUser(t.getId(),a.getAuditor());
//                            taskService.complete(t.getId(),hs2);
//                        }
//                    });
//                    break;
//                case "N":
//                    log.info("驳回  设置下一节点");
//                    HashMap<String, Object> hs3 = Maps.newHashMap();
//                    hs3.put("applyFlag","N");
//                    List<Task> list3 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//                    list3.forEach(t->{
//                        if(t.getTaskDefinitionKey().equals(a.getTaskDefKey())){
//                            taskService.addCandidateUser(t.getId(),a.getAuditor());
//                            taskService.complete(t.getId(),hs3);
//                        }else{
//                            log.info("该节点未找到此审核记录="+t.getTaskDefinitionKey());
//                        }
//                    });
//                    break;
//                case "T":
//                    log.info("转办  设置下一节点");
//                    List<Task> list4 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//                    list4.forEach(t->{
//                        if(t.getTaskDefinitionKey().equals(a.getTaskDefKey())){
//                            String transferredUser = a.getTransferredUser();
//                            String[] transusers = transferredUser.split(",");
//
//                            //首先删除之前人的userId
//                            //根据taskId查询当前节点所有审批人,然后删除所有
//                            //todo
//                            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(t.getId());
//                            identityLinksForTask.forEach(j->{
//                                if(!StringUtils.isEmpty(j.getUserId())) {
//                                    taskService.deleteCandidateUser(t.getId(), j.getUserId());
//                                }
//                            });
//                            //taskService.deleteCandidateUser(taskParam.getTaskId(), taskParam.getUserId());
//                            for (int i = 0; i <transusers.length ; i++) {
//                                log.info("任务Id="+t.getId()+" 被转办人Id="+transusers[i]);
//                                taskService.addCandidateUser(t.getId(), transusers[i]);
//                            }
//
//                        }else{
//                            log.info("该节点未找到此审核记录="+t.getTaskDefinitionKey());
//                        }
//                    });
//                    break;
//                case "E":
//                    log.info("加签  设置下一节点");
//                    List<Task> list5 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//                    list5.forEach(t->{
//                        if(t.getTaskDefinitionKey().equals(a.getTaskDefKey())){
//
//                            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(t.getId());
//                            identityLinksForTask.forEach(j->{
//                                if(!StringUtils.isEmpty(j.getUserId())) {
////                                    if(!j.getUserId().equals(a.getAuditor())) {
//                                        taskService.deleteCandidateUser(t.getId(), j.getUserId());
////                                    }
//                                }
//                            });
//                            taskService.addCandidateUser(t.getId(),a.getAuditor());
//                            taskService.delegateTask(t.getId(), a.getPrincipalUser());
//
//                        }else{
//                            log.info("该节点未找到此审核记录="+t.getTaskDefinitionKey());
//                        }
//                    });
//                    break;
//                default:
//                    log.error("未找到该决策");
//                    break;
//            }
//
//        });

        //描述：修改basicForm表单的流程
        BasicForm basicForm = new BasicForm();
        basicForm.setId(activateProcessParam.getBasicInfoId());
        basicForm.setInstanceId(processInstance.getId());
        basicForm.setUpdateTm(new Date());
        basicForm.setUpdatedBy(CurrentUserUtil.getCurrentUserId());
        if (basicFormMapper.updateById(basicForm) != 1) {
            throw new BusinessException("修改表单的实例id失败");
        }
        //描述：记录操作日志
        saveBizProcessOperLog(OperLogConstant.ACTIVATE, OperLogConstant.ACTIVATE_REMARK, activateProcessParam.getBasicInfoId(), activateProcessParam.getProcDefKey(), activateProcessParam.getProcInstId(), null, null, null, null, null);


    }

    /**
     * 取消流程
     *
     * @param taskParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancleProcess(TaskParam taskParam) {
        gtaAsyncTask.cancleTask(taskParam);
        //todo 记录日志
        saveBizProcessOperLog(OperLogConstant.CANCLE, OperLogConstant.CANCLE_REMARK, taskParam.getBasicInfoId(), taskParam.getProcessDefKey(), taskParam.getProcessInstId(), null, null, null, null, null);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processEnd(TaskParam taskParam) {
        gtaAsyncTask.endTask(taskParam);
        //todo 记录日志
        saveBizProcessOperLog(OperLogConstant.PROCESS_END, OperLogConstant.PROCESS_END_REMARK, taskParam.getBasicInfoId(), taskParam.getProcessDefKey(), taskParam.getProcessInstId(), null, null, null, null, null);

    }


    @Override
    public void saveBizProcessOperLog(String action, String remark, String basicInfoId, String procDefKey, String procInstId, String taskDefKey, String taskId, String targetTaskDefKey, String auditors, String newProcInstId) {
        BizProcessOperLog bizProcessOperLog = new BizProcessOperLog();
        bizProcessOperLog.setBasicInfoId(basicInfoId);
        bizProcessOperLog.setProcDefKey(procDefKey);
        bizProcessOperLog.setProcInstId(procInstId);
        bizProcessOperLog.setAction(action);
        bizProcessOperLog.setRemark(remark);

        if (!StringUtils.isEmpty(newProcInstId)) {
            bizProcessOperLog.setNewProcInstId(newProcInstId);
        }

        if (!StringUtils.isEmpty(taskDefKey)) {
            bizProcessOperLog.setTaskDefKey(taskDefKey);
        }

        if (!StringUtils.isEmpty(taskId)) {
            bizProcessOperLog.setTaskId(taskId);
        }

        if (!StringUtils.isEmpty(taskId)) {
            bizProcessOperLog.setTaskId(taskId);
        }

        if (!StringUtils.isEmpty(targetTaskDefKey)) {
            bizProcessOperLog.setTargetTaskDefKey(targetTaskDefKey);
        }

        if (!StringUtils.isEmpty(auditors)) {
            bizProcessOperLog.setAuditors(auditors);
        }
        bizProcessOperLog.setCreateTm(new Date());
        bizProcessOperLog.setCreatedBy(CurrentUserUtil.getCurrentUserId());
        log.info("action=" + bizProcessOperLog.getAction() + "  日志入库参数=" + JSON.toJSONString(bizProcessOperLog));
        if (bizProcessOperLogMapper.insert(bizProcessOperLog) != 1) {
            throw new RuntimeException("记录日志失败");
        }
    }
}
