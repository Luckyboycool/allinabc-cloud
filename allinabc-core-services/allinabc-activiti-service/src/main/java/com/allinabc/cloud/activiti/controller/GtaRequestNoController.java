package com.allinabc.cloud.activiti.controller;

import com.allinabc.cloud.activiti.pojo.vo.NodeModel;
import com.allinabc.cloud.activiti.service.GtaRequestNoService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/5 13:38
 **/
@RestController
@RequestMapping("/requestNo")
public class GtaRequestNoController {
    @Autowired
    private GtaRequestNoService gtaRequestNoService;

    /**
     * 获取所有节点信息
     * @return
     */
    @GetMapping("/get")
    @ApiOperation(value = "获取requestNo")
    public Result<String> getRequestNo(@RequestParam("pre") String pre,@RequestParam("length") Integer length ){
        String requestNo = gtaRequestNoService.getRequestNo(pre, length);
        return Result.success(requestNo);

    }
}
