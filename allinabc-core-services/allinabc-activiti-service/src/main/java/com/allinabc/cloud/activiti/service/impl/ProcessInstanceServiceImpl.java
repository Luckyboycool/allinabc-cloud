package com.allinabc.cloud.activiti.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.DecisionAdapt;
import com.allinabc.cloud.activiti.config.ExecuteManager;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.mapper.NodeDecisionMapper;
import com.allinabc.cloud.activiti.mapper.TaskMapper;
import com.allinabc.cloud.activiti.pojo.bo.AuditorBO;
import com.allinabc.cloud.activiti.pojo.bo.AuditorModel;
import com.allinabc.cloud.activiti.pojo.bo.NameModel;
import com.allinabc.cloud.activiti.pojo.consts.DecisionConstant;
import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.enums.TaskEnum;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.po.BizAudit;
import com.allinabc.cloud.activiti.pojo.vo.BizAuditVO;
import com.allinabc.cloud.activiti.pojo.vo.CommentModel;
import com.allinabc.cloud.activiti.pojo.vo.DecisionVO;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.allinabc.cloud.activiti.service.BizAuditService;
import com.allinabc.cloud.activiti.service.FormPermissionService;
import com.allinabc.cloud.activiti.service.IProcessInstanceService;
import com.allinabc.cloud.activiti.service.NodeAuditorService;
import com.allinabc.cloud.activiti.util.FormNameUtils;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.SortParamUtils;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/24 16:58
 **/
@Service
@Slf4j
public class ProcessInstanceServiceImpl implements IProcessInstanceService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private GtaAsyncTask gtaAsyncTask;

    @Resource
    private NodeAuditorMapper nodeAuditorMapper;

    @Resource
    private BasicFormMapper basicFormMapper;


    @Autowired
    private TaskMapper taskMapper;

//    @Resource
//    private BizAuditMapper bizAuditMapper;
//
//
//    @Resource
//    private BizGroupAuditorMapper bizGroupAuditorMapper;

    @Autowired
    private BizAuditService bizAuditService;

    @Resource
    private NodeDecisionMapper nodeDecisionMapper;

    @Autowired
    private FormPermissionService formPermissionService;

    @Autowired
    private NodeAuditorService nodeAuditorService;

    @Autowired
    private ExecuteManager executeManager;


    /**
     * 启动一个流程
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskModel startProcess(ProcessStartParam processStartParam) {
        log.info("启动流程实例参数:" + JSON.toJSONString(processStartParam));
        //设置流程发起人
        if (!StringUtils.isEmpty(processStartParam.getRequester())) {
            identityService.setAuthenticatedUserId(processStartParam.getRequester());
        }
        log.info("开始启动流程。。。。。");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processStartParam.getProcessDefinitionKey(), processStartParam.getBusinessId(), processStartParam.getVariables());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //启动好之后，完成任务
        //默认设置启动流程的人为审核人
        taskService.addCandidateUser(task.getId(), processStartParam.getRequester());
        //	设置审批人，一般从session中获得
//        Authentication.setAuthenticatedUserId(processStartParam.getRequester());
//        CommentBO commentBO = getCommentBO(ActionTypeConstant.CREATE, "",null,null);
//        taskService.addComment(task.getId(), processInstance.getId(),JSON.toJSONString(commentBO));
        ProcessExecuteParam processExecuteParam = new ProcessExecuteParam();
        processExecuteParam.setTaskId(task.getId());
        processExecuteParam.setInstanceId(processInstance.getId());
        processExecuteParam.setComment(processStartParam.getComment());
        processExecuteParam.setUserId(processStartParam.getRequester());
        bizAuditService.addComment(processInstance, processExecuteParam, DecisionConstant.SUBMIT, task, null, null,processStartParam.getVariables());
        //描述：往formPermision表中存入数据
        User currentUser = CurrentUserUtil.getCurrentUser();
        formPermissionService.savePermission(true,processStartParam.getBusinessId(),currentUser.getAccountType(),processStartParam.getRequester(),processStartParam.getVariables(),task.getTaskDefinitionKey(),processStartParam.getProcessDefinitionKey(),processInstance.getStartUserId());
        taskService.complete(task.getId(), processStartParam.getVariables());
        //设置下一节点
        Task taskIng = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        nodeAuditorService.setAuditors(taskIng.getId(), taskIng.getTaskDefinitionKey(), processStartParam.getProcessDefinitionKey(), processInstance.getId(), processInstance.getStartUserId(), processStartParam.getVariables());
        log.info("启动好之后开始完成第一个任务。。。。");
        TaskModel taskModel = new TaskModel();
        taskModel.setInstanceId(task.getProcessInstanceId());
        taskModel.setProcessDefinitionKey(processStartParam.getProcessDefinitionKey());
        return taskModel;
    }

    /**
     * 完成一个任务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> executeProcess(ProcessExecuteParam processExecuteParam) {
        log.info("完成任务请求参数：" + JSON.toJSONString(processExecuteParam));
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processExecuteParam.getInstanceId()).singleResult();
        Task currentTask = taskService.createTaskQuery().taskId(processExecuteParam.getTaskId()).processInstanceId(processExecuteParam.getInstanceId()).singleResult();
        if(currentTask==null){
            throw new RuntimeException("当前任务已完成");
        }
        Map<String, Object> variables = processExecuteParam.getVariables();
        Object obj = variables.get("applyFlag");
        User currentUser = CurrentUserUtil.getCurrentUser();
        String basicInfoId = (String) variables.get("basicInfoId");
        if(StringUtils.isEmpty(basicInfoId)){
            throw new RuntimeException("basicFormID参数为空");
        }
        if (obj == null) {
            log.info("如果没传的话，直接默认按照同意执行流程");
            DecisionAdapt decisionService = executeManager.getDecisionService("node_decision_NOAPPLYFLAG");
            return decisionService.complateTask(processInstance, processExecuteParam, currentTask, currentUser, basicInfoId, variables);
        } else {
            String applyFlag = (String) obj;
            DecisionAdapt decisionService = executeManager.getDecisionService("node_decision_" + applyFlag);
            if(decisionService!=null){
                return decisionService.complateTask(processInstance, processExecuteParam, currentTask, currentUser, basicInfoId, variables);
            }else{
                throw new RuntimeException("该审批决策未找到实现类！！！！！");
            }
        }

    }

    /**
     * 根据流程
     *
     * @param instanceId
     * @return
     */
    @Override
    public List<String> findTaskIdByInstanceId(String instanceId) {
        List<Task> list = taskService.createTaskQuery().processInstanceId(instanceId).list();
        List<String> ls = Lists.newArrayList();
        if (!CollUtil.isEmpty(ls)) {
            list.forEach(i -> {
                ls.add(i.getId());
            });
            return ls;

        } else {
            return null;
        }
    }

    /**
     * 根据basicInfoId获取流程节点信息
     *
     * @param basicInfoId
     * @return
     */
    @Override
    public List<TaskModel> findBizNodeInfoByBizId(String basicInfoId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(basicInfoId).singleResult();
        if (processInstance == null) {
            log.info("此流程已结束");
            List<TaskModel> objects = Lists.newArrayList();
            TaskModel taskModel = new TaskModel();
            taskModel.setTaskName(TaskEnum.END.getName());
            taskModel.setTaskDefinitionKey(TaskEnum.END.getCode());
            List<AuditorBO> auditorBOS = Lists.newArrayList();
            AuditorBO auditorBO = new AuditorBO();
            List<AuditorModel> auditorModelList = Lists.newArrayList();
            AuditorModel auditorModel = new AuditorModel();
            auditorModel.setAuditorName("NA");
            auditorModel.setNameAndJobNumber("NA");
            auditorModelList.add(auditorModel);
            auditorBO.setAuditorModels(auditorModelList);
            auditorBOS.add(auditorBO);
            taskModel.setAuditors(auditorBOS);
            objects.add(taskModel);
            return objects;
        }
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        ArrayList<TaskModel> ls = Lists.newArrayList();
        list.forEach(i -> {
            TaskModel taskModel = setTaskModel(i, processInstance.getProcessDefinitionKey(), null);
            ls.add(taskModel);
        });
        return ls;
    }

    /**
     * 根据taskId获取流程节点信息
     *
     * @param taskParam
     * @return
     */
    @Override
    public TaskModel findBizNodeInfoByTaskId(TaskParam taskParam) {
//        if (!StringUtils.isEmpty(taskParam.getTaskType()) && taskParam.getTaskType().equals("done")) {
//            //已办
//            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskParam.getTaskId()).processDefinitionKey(taskParam.getProcessDefKey()).singleResult();
//            return setTaskModel(null, taskParam.getProcessDefKey(), historicTaskInstance);
//        }
        //未办
        Task task = taskService.createTaskQuery().taskId(taskParam.getTaskId()).processDefinitionKey(taskParam.getProcessDefKey()).singleResult();
        TaskModel taskModel = setTaskModel(task, taskParam.getProcessDefKey(), null);
        return taskModel;
    }


    /**
     * 查询待办
     *
     * @param taskParam
     * @return
     */
//    @Override
//    public Page<TaskModel> findTaskIng(TaskParam taskParam) {
//        TaskQuery query = taskService.createTaskQuery().taskCandidateOrAssigned(taskParam.getUserId()).orderByTaskCreateTime().desc();
//        if (StrUtil.isNotBlank(taskParam.getProcessDefKey())) {
//            query.processDefinitionKey(taskParam.getProcessDefKey());
//        }
//        long count = query.count();
//        Long first = (taskParam.getPageNum() - 1) * taskParam.getPageSize();
//        List<Task> taskList = query.listPage(Integer.parseInt(first + ""), Integer.parseInt(taskParam.getPageSize() + ""));
//        List<TaskModel> list = Lists.newArrayList();
//        if (null != taskList && taskList.size() > 0) {
//            taskList.forEach(rt -> {
//                TaskModel tk = new TaskModel(rt.getId(), rt.getName(), rt.getAssignee(), rt.getTaskDefinitionKey());
//                // List<IdentityLink> identityLinks = runtimeService.getIdentityLinksForProcessInstance(rt.getProcessInstanceId());
//                // setApplyer(tk, null, identityLinks);
//                // 关联业务key
//                ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(rt.getProcessInstanceId()).singleResult();
//                tk.setBusinessId(pi.getBusinessKey());
//                tk.setInstanceId(pi.getId());
//                tk.setProcessDefinitionKey(pi.getProcessDefinitionKey());
//                tk.setProcessName(pi.getName());
//                tk.setProcessDefinitionName(pi.getProcessDefinitionName());
//                list.add(tk);
//            });
//
//        }
//        //描述：查询basicForm信息
//        setBasicFormInfo(list);
//        return new Page<>(taskParam.getPageNum(), taskParam.getPageSize(), null, count, list);
//    }


    /**
     * 查询已办
     *
     * @param taskParam
     * @return
     */
//    @Override
//    public Page<TaskModel> findTaskDone(TaskParam taskParam) {
//        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(taskParam.getUserId()).finished().orderByTaskCreateTime().desc();
//        if (StrUtil.isNotBlank(taskParam.getProcessDefKey())) {
//            query.processDefinitionKey(taskParam.getProcessDefKey());
//        }
//        long count = query.count();
//        List<TaskModel> list = Lists.newArrayList();
//        Long first = (taskParam.getPageNum() - 1) * taskParam.getPageSize();
//        List<HistoricTaskInstance> taskList = query.listPage(Integer.parseInt(first + ""), Integer.parseInt(taskParam.getPageSize() + ""));
//        if (taskList != null && taskList.size() > 0) {
//            taskList.forEach(tk -> {
//                TaskModel taskModel = new TaskModel();
//                taskModel.setTaskId(tk.getId());
//                taskModel.setTaskName(tk.getName());
//                taskModel.setTaskDefinitionKey(tk.getTaskDefinitionKey());
//
//                //List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(tk.getId());
//                //setApplyer(taskModel, historicIdentityLinksForTask, null);
//
//                //设置：批注
//                // setCommentModel(taskModel,tk.getId());
//
//                HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(taskParam.getProcessDefKey()).processInstanceId(tk.getProcessInstanceId()).singleResult();
//                setProcessTaskModel(taskModel, pi);
//                list.add(taskModel);
//            });
//        }
//        setBasicFormInfo(list);
//        //根据实例ID去重
//        List<TaskModel> collect = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TaskModel::getInstanceId))), ArrayList::new));
//
//        return new Page<>(taskParam.getPageNum(), taskParam.getPageSize(), null, count, collect);
//
//    }

    /**
     * 查询流转历史
     *
     * @param taskParam
     * @return
     */
    @Override
    public List<TaskModel> findTaskFlow(TaskParam taskParam) {
        List<BizAuditVO> taskList = bizAuditService.selectTaskCommentByProcessId(taskParam.getProcessInstId());
        List<TaskModel> list = Lists.newArrayList();
        if (taskList != null && taskList.size() > 0) {
            taskList.forEach(tk -> {
                TaskModel taskModel = new TaskModel();
                taskModel.setTaskId(tk.getTaskId());
                taskModel.setTaskName(tk.getTaskName());
                taskModel.setTaskDefinitionKey(tk.getTaskDefKey());

                //设置：批注
                setCommentModel(taskModel, tk.getId());

                HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(taskParam.getProcessDefKey()).processInstanceId(taskParam.getProcessInstId()).singleResult();
                setProcessTaskModel(taskModel, pi);
                list.add(taskModel);
            });
        }
        return list;
    }

    private void setCommentModel(TaskModel taskModel, String commentId) {
        BizAudit comment = bizAuditService.selectByCommentId(commentId);
        //描述：设置评论
        List<CommentModel> commentList = Lists.newArrayList();
        CommentModel commentModel = new CommentModel();
        commentModel.setId(comment.getId());
        commentModel.setInstanceId(comment.getProcInstId());
        commentModel.setTaskId(comment.getTaskId());
        commentModel.setCommentTm(comment.getCreateTm());
        commentModel.setComment(comment.getDecision());
        if (comment.getAction().equals(DecisionConstant.SUBMIT)) {
            commentModel.setAction("Submit(提交)");
        } else {
            //判断是否是加签的
            DecisionVO decisionVO = null;
            if(comment.getAction().equals("C_Y")||comment.getAction().equals("C_N")) {
                decisionVO = nodeDecisionMapper.selectDecisionNameBykey(comment.getAction(), "common");
            }else{
                decisionVO = nodeDecisionMapper.selectDecisionNameBykey(comment.getAction(), taskModel.getTaskDefinitionKey());
            }
            commentModel.setAction(decisionVO.getBlendDecisionName());
        }
        //查询姓名
        NameModel nameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType(comment.getUserType(), comment.getAuditor());
        if (nameModel != null) {
            commentModel.setName(nameModel.getName());
            commentModel.setNameAndJobNumber(nameModel.getNameAndJobNumber());
        }
        //设置用户ID
        commentModel.setUserId(comment.getAuditor());
        commentList.add(commentModel);
        taskModel.setComment(commentList);
    }


    /**
     * 查询流转历史
     *
     * @param taskParam
     * @return
     */
//    @Override
//    public List<TaskModel> findTaskFlow(TaskParam taskParam) {
//        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(taskParam.getProcessInstId()).finished().orderByTaskCreateTime().desc().list();
//        List<TaskModel> list = Lists.newArrayList();
//        if (taskList != null && taskList.size() > 0) {
//            taskList.forEach(tk -> {
//                TaskModel taskModel = new TaskModel();
//                taskModel.setTaskId(tk.getId());
//                taskModel.setTaskName(tk.getName());
//                taskModel.setTaskDefinitionKey(tk.getTaskDefinitionKey());
//
//                //List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(tk.getId());
//                //setApplyer(taskModel, historicIdentityLinksForTask, null);
//                //设置：批注
//                setCommentModel(taskModel, tk.getId());
//
//                HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(taskParam.getProcessDefKey()).processInstanceId(taskParam.getProcessInstId()).singleResult();
//                setProcessTaskModel(taskModel, pi);
//                list.add(taskModel);
//            });
//        }
//        return list;
//    }


    /**
     * taskModel参数封装
     *
     * @param task
     * @param processDefinitionKey
     * @return
     */
    public TaskModel setTaskModel(Task task, String processDefinitionKey, HistoricTaskInstance historicTaskInstance) {
        if (null != task) {
            TaskModel taskModel = new TaskModel();
            taskModel.setTaskId(task.getId());
            taskModel.setInstanceId(task.getProcessInstanceId());
            taskModel.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskModel.setTaskName(task.getName());
            taskModel.setAssignee(task.getAssignee());
            taskModel.setProcessDefinitionKey(processDefinitionKey);
            List<AuditorBO> ls = Lists.newArrayList();
            //描述：查询节点审批人
            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
            List<AuditorModel> auditorModelList = Lists.newArrayList();

            if(CollUtil.isEmpty(identityLinksForTask)){
                AuditorModel auditorModel = new AuditorModel();
                auditorModel.setAuditorName("NA");
                auditorModel.setNameAndJobNumber("NA");
                auditorModelList.add(auditorModel);

            }else{
                log.info("有节点信息");
                DelegationState delegationState = task.getDelegationState();
                if(null != delegationState && delegationState.toString().equals("PENDING")){
                    log.info("节点为加签节点");
                    //加签节点特殊处理（业务要求！！）
                    taskModel.setTaskName("加签");
                    identityLinksForTask.stream().distinct().forEach(j->{
                        if(j.getType().equals("assignee")){
                            log.info("该节点的加签处理人为"+j.getUserId());
                            AuditorModel auditorModel = new AuditorModel();
                            auditorModel.setAuditorKey(j.getUserId());
                            NameModel nameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType("1", j.getUserId());
                            if (nameModel != null) {
                                auditorModel.setAuditorName(nameModel.getName());
                                auditorModel.setNameAndJobNumber(nameModel.getNameAndJobNumber());
                            }else{
                                //可能是客户
                                NameModel accountNameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType("2", j.getUserId());
                                if(accountNameModel!=null) {
                                    auditorModel.setAuditorName(accountNameModel.getName());
                                    auditorModel.setNameAndJobNumber(accountNameModel.getNameAndJobNumber());
                                }
                            }
                            auditorModelList.add(auditorModel);
                        }
                    });

                }else{

                    identityLinksForTask.forEach(j -> {
                        AuditorModel auditorModel = new AuditorModel();
                        auditorModel.setAuditorKey(j.getUserId());
                        NameModel nameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType("1", j.getUserId());
                        if (nameModel != null) {
                            auditorModel.setAuditorName(nameModel.getName());
                            auditorModel.setNameAndJobNumber(nameModel.getNameAndJobNumber());
                        }else{
                            //可能是客户
                            NameModel accountNameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType("2", j.getUserId());
                            if(accountNameModel!=null) {
                                auditorModel.setAuditorName(accountNameModel.getName());
                                auditorModel.setNameAndJobNumber(accountNameModel.getNameAndJobNumber());
                            }
                        }
                        auditorModelList.add(auditorModel);
                    });

                }

            }
            AuditorBO auditorBO = new AuditorBO();
            auditorBO.setAuditorModels(auditorModelList);
            ls.add(auditorBO);
            taskModel.setAuditors(ls);
            return taskModel;
        } else if (historicTaskInstance != null) {
//            TaskModel taskModel = new TaskModel();
//            taskModel.setTaskId(historicTaskInstance.getId());
//            taskModel.setInstanceId(historicTaskInstance.getProcessInstanceId());
//            taskModel.setTaskDefinitionKey(historicTaskInstance.getTaskDefinitionKey());
//            taskModel.setTaskName(historicTaskInstance.getName());
//            taskModel.setAssignee(historicTaskInstance.getAssignee());
//            taskModel.setProcessDefinitionKey(processDefinitionKey);
//            List<AuditorBO> ls = Lists.newArrayList();
//
//
//            List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(historicTaskInstance.getId());
//            List<AuditorModel> auditorModelList = Lists.newArrayList();
//            historicIdentityLinksForTask.forEach(j -> {
//                AuditorModel auditorModel = new AuditorModel();
//                auditorModel.setAuditorKey(j.getUserId());
//                NameModel nameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType("1", j.getUserId());
//                if (nameModel != null) {
//                    auditorModel.setAuditorName(nameModel.getName());
//                    auditorModel.setNameAndJobNumber(nameModel.getNameAndJobNumber());
//                }else{
//                    //可能是客户
//                    NameModel accountNameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType("2", j.getUserId());
//                    if(accountNameModel!=null) {
//                        auditorModel.setAuditorName(accountNameModel.getName());
//                        auditorModel.setNameAndJobNumber(accountNameModel.getNameAndJobNumber());
//                    }
//                }
//                auditorModelList.add(auditorModel);
//            });
//            if(CollUtil.isEmpty(historicIdentityLinksForTask)){
//                AuditorModel auditorModel = new AuditorModel();
//                auditorModel.setAuditorName("NA");
//                auditorModel.setNameAndJobNumber("NA");
//                auditorModelList.add(auditorModel);
//
//            }
//            AuditorBO auditorBO = new AuditorBO();
//            auditorBO.setAuditorModels(auditorModelList);
//            taskModel.setAuditors(ls);
//            return taskModel;
            return null;
        } else {
            return null;
        }
    }



    public void setProcessTaskModel(TaskModel taskModel, HistoricProcessInstance pi) {
        if (pi != null) {
            taskModel.setBusinessId(pi.getBusinessKey());
            taskModel.setInstanceId(pi.getId());
            taskModel.setProcessDefinitionKey(pi.getProcessDefinitionKey());
            taskModel.setProcessName(pi.getName());
            taskModel.setProcessDefinitionName(pi.getProcessDefinitionName());
        }
    }



    /**
     * 获取basicForm的值，并设置taskModel的信息
     */
    public void setBasicFormInfo(List<TaskModel> list) {
        if (list != null) {
            list.forEach(taskModel -> {
                BasicForm basicForm = basicFormMapper.selectById(taskModel.getBusinessId());
                if (basicForm != null) {
                    taskModel.setSubject(basicForm.getSubject());
                    taskModel.setRequestNo(basicForm.getRequestNo());
                    taskModel.setRequestTime(basicForm.getRequestTime());
                    taskModel.setRequester(basicForm.getRequester());
                    // String userName = nodeAuditorMapper.selectUserNameByUserType(basicForm.getUserType(),basicForm.getRequester());
                    NameModel nameModel = nodeAuditorMapper.selectUserNameAndJobNumByUserType(basicForm.getUserType(), basicForm.getRequester());
                    //将流程发起人设置到申请人当中
                    if (nameModel != null) {
                        taskModel.setRequesterName(nameModel.getName());
                        taskModel.setNameAndJobNumber(nameModel.getNameAndJobNumber());
                    }
                }
            });
        }
    }




    /**
     * 转办任务
     *
     * @param taskParam
     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void transferTask(TaskParam taskParam) {
//        log.info("转办任务参数=" + JSON.toJSONString(taskParam));
//        if (StringUtils.isEmpty(taskParam.getTransUserId())) {
//            throw new RuntimeException("被转办人ID不能为空");
//        }
//        //描述：transUserId可能为一个集合，用，隔开所以需要先分割
//        String[] transUserIds = taskParam.getTransUserId().split(",");
//        //首先删除之前人的userId
//        //根据taskId查询当前节点所有审批人,然后删除所有
//        //todo
//        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskParam.getTaskId());
//        identityLinksForTask.forEach(j->{
//            if(!StringUtils.isEmpty(j.getUserId())) {
//                taskService.deleteCandidateUser(taskParam.getTaskId(), j.getUserId());
//            }
//        });
//
//        for (int i = 0; i <transUserIds.length ; i++) {
//            log.info("任务Id="+taskParam.getTaskId()+"被转办人Id="+transUserIds[i]);
//            taskService.addCandidateUser(taskParam.getTaskId(), transUserIds[i]);
//        }
//
//        //如果要查询转给他人处理的任务，可以同时将OWNER进行设置：
//        //taskService.setOwner(taskParam.getTaskId(),taskParam.getUserId());
//        log.info("转办成功");
//    }

    /**
     * 委托任务
     *
     * @param taskParam
     */
//    @Transactional(rollbackFor = Exception.class)
//    public void delegateTask(TaskParam taskParam) {
//        log.info("委托任务参数=" + JSON.toJSONString(taskParam));
//        if (StringUtils.isEmpty(taskParam.getDelegateUserId())) {
//            throw new BusinessException("被委托人ID不能为空");
//        }
//        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskParam.getTaskId());
//        identityLinksForTask.forEach(j->{
//            if(!StringUtils.isEmpty(j.getUserId())) {
//                if(!j.getUserId().equals(taskParam.getUserId())) {
//                    taskService.deleteCandidateUser(taskParam.getTaskId(), j.getUserId());
//                }
//            }
//        });
//        taskService.delegateTask(taskParam.getTaskId(), taskParam.getDelegateUserId());
//        log.info("委派成功");
//    }


    /**
     * 模糊查询待办任务
     *
     * @param taskParam
     * @return
     */
    @Override
    public Page<TaskModel> findTaskIngSearch(TaskParam taskParam) {
        taskParam.setSortParam(SortParamUtils.handleSortParam(taskParam.getSortParam()));

        IPage<TaskModel> page = new Page<TaskModel>(taskParam.getPageNum(), taskParam.getPageSize());
        Page<TaskModel> taskModelPage;
        if (taskParam.getIsFuzzyQuery()) {
            //模糊查询
            String processDefKey = taskParam.getProcessDefKey();
            String[] split = processDefKey.split(",");
            List<String> processDefKeyList = Arrays.asList(split);
            taskModelPage = taskMapper.findTaskIngFuzzySearch(page, processDefKeyList,taskParam);
        } else {
            //非模糊查询
            taskModelPage = taskMapper.findTaskIng(page, taskParam);
        }

        taskModelPage.getRecords().forEach(a-> {
            a.setFormName(FormNameUtils.getFormName(a.getFormType()));
        });
        return taskModelPage;

    }

    /**
     * 模糊查询已办任务
     *
     * @param taskParam
     * @return
     */
    @Override
    public Page<TaskModel> findTaskDoneSearch(TaskParam taskParam) {
        taskParam.setSortParam(SortParamUtils.handleSortParam(taskParam.getSortParam()));
        IPage<TaskModel> page = new Page<>(taskParam.getPageNum(), taskParam.getPageSize());
        Page<TaskModel> taskModelPage;
        if (taskParam.getIsFuzzyQuery()) {
            //模糊查询
            String processDefKey = taskParam.getProcessDefKey();
            String[] split = processDefKey.split(",");
            List<String> processDefKeyList = Arrays.asList(split);
            taskModelPage = taskMapper.findTaskDoneFuzzySearch(page, processDefKeyList,taskParam);
        } else {
            //非模糊查询
            taskModelPage = taskMapper.findTaskDone(page, taskParam);
        }
        taskModelPage.getRecords().forEach(a-> {
            a.setFormName(FormNameUtils.getFormName(a.getFormType()));
        });
        return taskModelPage;

    }


}
