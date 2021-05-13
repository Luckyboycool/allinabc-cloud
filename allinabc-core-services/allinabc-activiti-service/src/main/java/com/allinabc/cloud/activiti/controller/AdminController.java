package com.allinabc.cloud.activiti.controller;

import cn.hutool.json.JSONUtil;
import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ActivateProcessParam;
import com.allinabc.cloud.activiti.pojo.params.AssignParam;
import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;
import com.allinabc.cloud.activiti.service.AdminService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/9 10:42
 **/
@RestController
@RequestMapping("/manage")
@Api("管理员操作接口")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 取消流程
     */
    @PostMapping("/process/cancle")
    @ApiOperation(value = "取消流程")
    public Result<Void> cancleProcess(@RequestBody TaskParam cancleParam){
        log.info("取消流程请求参数="+ JSONUtil.toJsonStr(cancleParam));
        adminService.cancleProcess(cancleParam);
        return Result.success();
    }


    /**
     * 结束流程
     */
    @PostMapping("/process/end")
    @ApiOperation(value = "结束流程")
    public Result<Void> processEnd(@RequestBody TaskParam taskParam){
        log.info("结束流程请求参数="+ JSONUtil.toJsonStr(taskParam));
        adminService.processEnd(taskParam);
        return Result.success();
    }


    /**
     * 根据节点指派给某个人任务(为没有节点审批人的节点指派人【可选择多个人】)
     */
    @PostMapping("/task/reassign")
    @ApiOperation(value = "根据节点指派给某个人任务")
    public Result<Void> assignNodeAuditor(@RequestBody AssignParam assignParam){
        adminService.assignNodeAuditor(assignParam);
        return Result.success();
    }



    /**
     * 指定跳转到某节点(节点：为指定节点ID)
     */
    @PostMapping("/node/grab")
    @ApiOperation(value = "指定跳转到某节点(节点：为指定节点ID)")
    public Result<Void> nodeJump(@RequestBody NodeJumpParam nodeJumpParam){
        if(StringUtils.isEmpty(nodeJumpParam.getProcInstId())){
            return Result.failed("流程实例ID为空");
        }
        if(StringUtils.isEmpty(nodeJumpParam.getTaskId())){
            return Result.failed("节点ID为空");
        }

        if(StringUtils.isEmpty(nodeJumpParam.getTargetNodeKey())){
            return Result.failed("目标节点KEY为空");
        }
        adminService.nodeJump(nodeJumpParam);
        return Result.success();
    }

    /**
     * 重新启动一个流程(用于激活流程异常的单据)
     */
    @PostMapping("/process/activate")
    @ApiOperation(value = "重新启动一个流程(用于激活流程异常的单据)")
    public Result<Void> restartNewProcessInst(@RequestBody ActivateProcessParam activateProcessParam){
        adminService.restartNewProcessInst(activateProcessParam);
        return Result.success();
    }
}
