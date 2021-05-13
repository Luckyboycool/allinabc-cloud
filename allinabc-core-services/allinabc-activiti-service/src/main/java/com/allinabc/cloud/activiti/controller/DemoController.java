//package com.allinabc.cloud.activiti.controller;
//
//import cn.hutool.core.util.IdUtil;
//import com.allinabc.cloud.activiti.mapper.LeaveMapper;
//import com.allinabc.cloud.activiti.pojo.dto.DemoParam;
//import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
//import com.allinabc.cloud.activiti.pojo.po.Leave;
//import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
//import com.allinabc.cloud.activiti.service.IProcessInstanceService;
//import com.allinabc.cloud.common.web.pojo.resp.Result;
//import com.google.common.collect.Maps;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.task.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description
// * @Author wangtaifeng
// * @Date 2021/3/1 16:05
// **/
//@RestController
//public class DemoController {
//
//    @Autowired
//    private IProcessInstanceService processInstanceService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Resource
//    private LeaveMapper leaveMapper;
//
//    /**
//     * 保存请假申请
//     * @return
//     */
//    @RequestMapping("/apply")
//    @Transactional
//    public Result<Void> applyLeave(String leaveDays,String reason,String requester){
//        //描述：启动流程
//        String id = IdUtil.getSnowflake(0, 0).nextIdStr();
//        HashMap<String, Object> hs = Maps.newHashMap();
//        hs.put("leaveDays",leaveDays);
//        ProcessStartParam processStartParam = new ProcessStartParam("allinabc_leave", id, requester, hs);
//        TaskModel taskModel = processInstanceService.startProcess(processStartParam);
//        Leave leave = new Leave();
//        leave.setId(id);
//        leave.setProcessInsId(taskModel.getInstanceId());
//        leave.setReason(reason);
//        leaveMapper.insert(leave);
//        return Result.success();
//    }
//
//    /**
//     * 设置流程节点审批人
//     */
//    @RequestMapping("/setaudit")
//    public Result<Void> setAuditPeople(@RequestBody DemoParam demoParam){
//        List<Map<String, String>> users = demoParam.getUsers();
//        users.stream().forEach(i->{
//            taskService.addCandidateUser(demoParam.getTaskId(),i.get("userId"));
//        });
//
//        return Result.success();
//    }
//
//    /**
//     * 获取审批人任务列表
//     */
//    @RequestMapping("/getaudit")
//    public Result getAuditPeople(String userId){
//        List<Task> list = taskService.createTaskQuery().taskCandidateUser(userId).orderByTaskCreateTime().desc().list();//查询所拥有的候选任务
//        return Result.success(list);
//    }
//
//
//
//    /**
//     * 通过当前bizId，查询当前节点信息
//     */
//    @RequestMapping("/nodeinfo")
//    public Result<List<TaskModel>> getNodeInfoBuBizId(String bizId){
//        return Result.success(processInstanceService.findBizNodeInfoByBizId(bizId));
//    }
//
//
//}
