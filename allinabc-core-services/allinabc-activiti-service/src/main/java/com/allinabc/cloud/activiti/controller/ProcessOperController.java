package com.allinabc.cloud.activiti.controller;

import com.allinabc.cloud.activiti.pojo.params.OperLogParam;
import com.allinabc.cloud.activiti.pojo.po.BizProcessOperLog;
import com.allinabc.cloud.activiti.service.ProcessOperService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 流程后台特殊处理操作日志
 * @Author wangtaifeng
 * @Date 2021/4/22 17:46
 **/
@Api("流程后台特殊处理操作日志接口")
@Slf4j
@RestController
@RequestMapping("/oper-log")
public class ProcessOperController {

    @Autowired
    private ProcessOperService processOperService;

    @PostMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询操作日志")
    public Result<Page<BizProcessOperLog>> findOperList(@PathVariable("pageNum") long pageNum,@PathVariable("pageSize") long pageSize, @RequestBody OperLogParam operLogParam){
        Page<BizProcessOperLog> page = processOperService.findOperList(pageNum,pageSize,operLogParam);
        return Result.success(page);
    }


    /**
     * 新增表单修改日志
     * @param operLogParam
     * @return
     */
    @PostMapping("/add-update")
    @ApiOperation(value = "新增表单修改日志")
    public Result<Void> addOperUpdateLog(@RequestBody OperLogParam operLogParam){
        processOperService.addOperUpdateLog(operLogParam);
        return Result.success();
    }
}
