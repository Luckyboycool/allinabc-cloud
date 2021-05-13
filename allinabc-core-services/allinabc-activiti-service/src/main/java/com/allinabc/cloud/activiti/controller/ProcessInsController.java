package com.allinabc.cloud.activiti.controller;

import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.allinabc.cloud.activiti.service.IProcessInstanceService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 流程实例
 */
@Slf4j
@RestController
@RequestMapping("/process/ins")
@Api(value = "流程实例接口列表")
public class ProcessInsController {

    @Autowired
    private IProcessInstanceService processInstanceService;

    @ApiOperation(value = "启动流程实例")
    @PostMapping("/start")
    public Result<TaskModel> startProcess(@RequestBody ProcessStartParam processStartParam) {
        log.info("启动流程实例");
        TaskModel taskModel = processInstanceService.startProcess(processStartParam);
        return Result.success(taskModel);

    }

    @ApiOperation(value = "执行流程")
    @PostMapping("/execute")
    public Result<List<String>> executeProcess(@RequestBody ProcessExecuteParam processExecuteParam) {
        log.info("执行流程");
        List<String> s = processInstanceService.executeProcess(processExecuteParam);
        return Result.success(s, "success");

    }

    @ApiOperation(value = "根据instancesId获取taskID")
    @GetMapping("/task/{id}")
    public Result<List<String>> findTaskIdByInstanceId(@PathVariable("id") String instanceId) {
        List<String> taskId = processInstanceService.findTaskIdByInstanceId(instanceId);
        return Result.success(taskId, "find success");

    }


    @GetMapping("/task/bizid")
    @ApiOperation(value = "通过业务Id，查询节点信息")
    public Result<List<TaskModel>> findBizNodeInfoByBizId(@RequestParam("basicInfoId") String basicInfoId){
        List<TaskModel>  taskModel = processInstanceService.findBizNodeInfoByBizId(basicInfoId);
        return Result.success(taskModel);

    }

    @PostMapping("/task/taskid")
    @ApiOperation(value = "通过任务Id，查询节点信息")
    public Result<TaskModel> findBizNodeInfoByTaskId(@RequestBody TaskParam taskParam){
        TaskModel  taskModel = processInstanceService.findBizNodeInfoByTaskId(taskParam);
        return Result.success(taskModel);

    }

//    @PostMapping("/task/ing")
//    @ApiOperation(value = "查询待办任务信息")
//    public Result<Page<TaskModel>> findTaskIng(@RequestBody TaskParam taskParam){
//        Page<TaskModel>  page = processInstanceService.findTaskIng(taskParam);
//        return Result.success(page);
//
//    }

//    @PostMapping("/task/done")
//    @ApiOperation(value = "查询已办信息")
//    public Result<Page<TaskModel>> findTaskDone(@RequestBody TaskParam taskParam){
//        Page<TaskModel>  page = processInstanceService.findTaskDone(taskParam);
//        return Result.success(page);
//    }

    @PostMapping("/task/flow")
    @ApiOperation(value = "查询流转历史,根据instanceId")
    public Result<List<TaskModel>> findTaskFlow(@RequestBody TaskParam taskParam){

        if(StringUtils.isEmpty(taskParam.getProcessInstId())){
            return Result.failed("processInstId can not null");
        }
        
        List<TaskModel> ls = processInstanceService.findTaskFlow(taskParam);
        return Result.success(ls);
    }




    @PostMapping("/task/ing/search")
    @ApiOperation(value = "模糊查询待办任务信息")
    public Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page<TaskModel>> findTaskIngSearch(@RequestBody TaskParam taskParam){
        if(taskParam.getIsFuzzyQuery()==null){
            return Result.failed("参数未设置");
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<TaskModel> page = processInstanceService.findTaskIngSearch(taskParam);
        return Result.success(page);
    }

    @PostMapping("/task/done/search")
    @ApiOperation(value = "模糊查询已办任务信息")
    public Result<Page<TaskModel>> findTaskDoneSearch(@RequestBody TaskParam taskParam){
        if(taskParam.getIsFuzzyQuery()==null){
            return Result.failed("参数未设置");
        }
        Page<TaskModel> page = processInstanceService.findTaskDoneSearch(taskParam);
        return Result.success(page);
    }





}
