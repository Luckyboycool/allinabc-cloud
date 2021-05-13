package com.allinabc.cloud.activiti.controller;

import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.activiti.service.FormPermissionService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/29 13:38
 **/
@RestController
@RequestMapping("/formPermission")
public class FormPermissionController {
    @Autowired
    private FormPermissionService formPermissionService;
    /**
     * 获取所有节点信息
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存formPermission")
    public Result<String> addPermission(@RequestBody FormPermission formPermission){
        formPermissionService.addPermission(formPermission);
        return Result.success();

    }
}
