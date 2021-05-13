//package com.allinabc.cloud.activiti.service.impl;
//
//import com.allinabc.cloud.activiti.mapper.BizBusinessMapper;
//import com.allinabc.cloud.activiti.pojo.consts.ActivitiConstant;
//import com.allinabc.cloud.activiti.pojo.po.BizBusiness;
//import com.allinabc.cloud.activiti.service.IBizBusinessService;
//import com.allinabc.cloud.activiti.service.IBizNodeService;
//import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
//import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
//import org.activiti.engine.IdentityService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@Service
//public class BizBusinessServiceImpl extends MybatisCommonServiceImpl<BizBusiness> implements IBizBusinessService {
//
//    @Resource
//    private BizBusinessMapper businessMapper;
//    @Resource
//    private RuntimeService    runtimeService;
//    @Resource
//    private IdentityService   identityService;
//    @Resource
//    private TaskService       taskService;
//    @Resource
//    private IBizNodeService     bizNodeService;
//
//    @Override
//    public void startProcess(BizBusiness business, Map<String, Object> variables) {
//        // 启动流程用户
//        identityService.setAuthenticatedUserId(business.getUserId().toString());
//        // 启动流程 需传入业务表id变量
//        ProcessInstance pi = runtimeService.startProcessInstanceById(business.getProcDefId(),
//                business.getId().toString(), variables);
//        // 设置流程实例名称
//        runtimeService.setProcessInstanceName(pi.getId(), business.getTitle());
//        BizBusiness bizBusiness = new BizBusiness().setId(business.getId()).setProcInstId(pi.getId())
//                .setProcDefKey(pi.getProcessDefinitionKey());
//        // 假如开始就没有任务，那就认为是中止的流程，通常是不存在的
//        setAuditor(bizBusiness, ActivitiConstant.RESULT_SUSPEND, business.getUserId());
//    }
//
//    @Override
//    public int setAuditor(BizBusiness business, int result, String currentUserId) {
//        List<Task> tasks = taskService.createTaskQuery().processInstanceId(business.getProcInstId()).list();
//        if (null != tasks && tasks.size() > 0) {
//            Task task = tasks.get(0);
//            Set<String> auditors = bizNodeService.getAuditors(task.getTaskDefinitionKey(), currentUserId);
//            if (null != auditors && auditors.size() > 0) {
//                // 添加审核候选人
//                for (String auditor : auditors) {
//                    taskService.addCandidateUser(task.getId(), auditor);
//                }
//                business.setCurrentTask(task.getName());
//            } else {
//                runtimeService.deleteProcessInstance(task.getProcessInstanceId(),
//                        ActivitiConstant.SUSPEND_PRE + "no auditor");
//                business.setCurrentTask(ActivitiConstant.END_TASK_NAME).setStatus(ActivitiConstant.STATUS_SUSPEND)
//                        .setResult(ActivitiConstant.RESULT_SUSPEND);
//            }
//        }
//        else {
//            // 任务结束
//            business.setCurrentTask(ActivitiConstant.END_TASK_NAME).setStatus(ActivitiConstant.STATUS_FINISH)
//                    .setResult(result);
//        }
//        super.update(business.getId(), business);
//        return 1;
//    }
//
//    @Override
//    protected MybatisCommonBaseMapper<BizBusiness> getRepository() {
//        return businessMapper;
//    }
//
//}
