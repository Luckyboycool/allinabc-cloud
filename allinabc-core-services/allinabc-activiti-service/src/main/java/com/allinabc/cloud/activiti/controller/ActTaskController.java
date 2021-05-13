///*
// * @(#)ActTaskController.java 2020年1月7日 下午6:15:46
// * Copyright 2020 zmr, Inc. All rights reserved.
// * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// */
//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.ActReProcdef;
//import com.allinabc.cloud.activiti.pojo.po.BizAudit;
//import com.allinabc.cloud.activiti.pojo.po.BizBusiness;
//import com.allinabc.cloud.activiti.service.IBizAuditService;
//import com.allinabc.cloud.activiti.service.IBizBusinessService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.cloud.common.web.pojo.resp.Result;
//import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import com.google.common.collect.Maps;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/task")
//public class ActTaskController extends MybatisBaseCrudController<ActReProcdef> {
//
//    @Autowired
//    private TaskService         taskService;
//    @Autowired
//    private IBizAuditService    bizAuditService;
//    @Autowired
//    private RuntimeService      runtimeService;
////    @Autowired
////    private ApiUserService      apiUserService;
//    @Autowired
//    private IBizBusinessService businessService;
//
////    /**
////     * task待办
////     *
////     * @return
////     * @author zmr
////     */
////    @RequestMapping(value = "/ing")
////    public Result ing(RuTask ruTask)
////    {
////        List<RuTask> list = new ArrayList<>();
////        String userId = CurrentUserUtil.getCurrentUserId();
////        TaskQuery query = taskService.createTaskQuery().taskCandidateOrAssigned(userId + "").orderByTaskCreateTime()
////                .desc();
////        if (StrUtil.isNotBlank(ruTask.getProcessDefKey())) {
////            query.processDefinitionKey(ruTask.getProcessDefKey());
////        }
////        long count = query.count();
////        int first = Math.toIntExact((ruTask.getPageNum() - 1) * ruTask.getPageSize());
////        int pageSize = Math.toIntExact(ruTask.getPageSize());
////        List<Task> taskList = query.listPage(first, pageSize);
////        if (taskList.size() > 0)
////        {
////            // 转换vo
////            taskList.forEach(e -> {
////                RuTask rt = new RuTask(e);
////                List<IdentityLink> identityLinks = runtimeService
////                        .getIdentityLinksForProcessInstance(rt.getProcInstId());
////                for (IdentityLink ik : identityLinks) {
////                    // 关联发起人
////                    if ("starter".equals(ik.getType()) && StrUtil.isNotBlank(ik.getUserId())){
////                        if(null == apiUserService.get(ik.getUserId()))
////                            continue;
////                        rt.setApplyer(apiUserService.get(ik.getUserId()).getUserName());
////                    }
////                }
////                // 关联业务key
////                ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(rt.getProcInstId())
////                        .singleResult();
////                rt.setBusinessKey(pi.getBusinessKey());
////                rt.setProcessName(pi.getName());
////                rt.setProcessDefKey(pi.getProcessDefinitionKey());
////                rt.setProcessDefName(pi.getProcessDefinitionName());
////                list.add(rt);
////            });
////        }
////        Map<String, Object> map = Maps.newHashMap();
////        map.put("rows", list);
////        map.put("pageNum", pageSize);
////        map.put("total", count);
////        return Result.success(map);
////    }
////
////    /**
////     * 获取 task 已办列表
////     * @param hiTaskVo
////     * @return
////     * @author zmr
////     */
////    @RequestMapping(value = "done")
////    public Result<List<HiTaskVo>> done(HiTaskVo hiTaskVo) {
////        // TODO change userId type to String
////        hiTaskVo.setAuditorId(CurrentUserUtil.getCurrentUserId());
////        hiTaskVo.setDeleteReason(ActivitiConstant.REASON_COMPLETED);
////        return Result.success(bizAuditService.getHistoryTaskList(hiTaskVo));
////    }
////
////    /**
////     * task 流转历史
////     *
////     * @param hiTaskVo
////     * @return
////     * @author zmr
////     */
////    @RequestMapping(value = "flow")
////    public Result<List<HiTaskVo>> flow(HiTaskVo hiTaskVo) {
////        return Result.success(bizAuditService.getHistoryTaskList(hiTaskVo));
////    }
////
//    /**
//     * 审批
//     *
//     * @param bizAudit
//     * @return
//     * @author zmr
//     */
//    @PostMapping("audit")
//    public Result<Void> audit(@RequestBody BizAudit bizAudit) {
//        Map<String, Object> variables = Maps.newHashMap();
//        variables.put("result", bizAudit.getResult());
//        // 审批
//        taskService.complete(bizAudit.getTaskId(), variables);
//        com.allinabc.cloud.common.core.domain.User currentUser = CurrentUserUtil.getCurrentUser();
//        bizAudit.setAuditor(currentUser.getUserName() + "-" + currentUser.getName());
//        bizAudit.setAuditorId(currentUser.getId());
//        bizAuditService.insert(bizAudit);
//        BizBusiness bizBusiness = new BizBusiness().setId(bizAudit.getBusinessKey())
//                .setProcInstId(bizAudit.getProcInstId());
//        businessService.setAuditor(bizBusiness, bizAudit.getResult(), CurrentUserUtil.getCurrentUserId());
//        return Result.success();
//    }
//
////    @PostMapping("audit/batch")
////    public Result<Void> auditBatch(@RequestBody BizAudit bizAudit)
////    {
////        User user = apiUserService.get(CurrentUserUtil.getCurrentUserId());
////
////        for (String taskId : bizAudit.getTaskIds())
////        {
////            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
////            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
////                    .processInstanceId(task.getProcessInstanceId()).singleResult();
////            BizBusiness bizBusiness = businessService.selectBizBusinessById(pi.getBusinessKey());
////            if (null != bizBusiness)
////            {
////                Map<String, Object> variables = Maps.newHashMap();
////                variables.put("result", bizAudit.getResult());
////                // 审批
////                taskService.complete(taskId, variables);
////                // 构建插入审批记录
////                BizAudit audit = new BizAudit().setTaskId(taskId).setResult(bizAudit.getResult())
////                        .setProcName(bizBusiness.getProcName()).setProcDefKey(bizBusiness.getProcDefKey())
////                        .setApplyer(bizBusiness.getApplyer())
////                        .setAuditor(user.getUserName() + "-" + user.getNickName())
////                        .setAuditorId(user.getId());
////                bizAuditService.insertBizAudit(audit);
////                businessService.setAuditor(bizBusiness, audit.getResult(), CurrentUserUtil.getCurrentUserId());
////            }
////        }
////        return Result.success();
////    }
//
//
//    @Override
//    protected CommonService<ActReProcdef> getService() {
//        return null;
//    }
//
//}
