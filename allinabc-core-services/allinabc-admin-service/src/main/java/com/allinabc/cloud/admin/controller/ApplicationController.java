package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.po.Application;
import com.allinabc.cloud.admin.pojo.vo.AppPathVO;
import com.allinabc.cloud.admin.pojo.vo.ApplicationVO;
import com.allinabc.cloud.admin.service.ApplicationService;
import com.allinabc.cloud.common.core.mvc.BaseController;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.cloud.starter.log.annotation.OperLog;
import com.allinabc.cloud.starter.log.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     代码迁移
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-14
 */
@Slf4j
@Api(tags = "application配置")
@RestController
@RequestMapping("/application")
@BizClassification(serviceName = "admin",modelName = "application",bizName = "application配置")
public class ApplicationController extends BaseController<Application> {

    @Autowired
    private ApplicationService applicationService;

    @Override
    public Result findList(QueryParam queryParam) {
        List<AppPathVO> voList = applicationService.findAppPaht();
        return Result.success(voList);
    }

    @ApiOperation("获取系统配置信息")
    @RequestMapping(value = {"/get/appCode"}, method = {RequestMethod.GET})
    @OperLog(title = "获取系统配置信息",businessType = BusinessType.OTHER)
    public Result<List<ApplicationVO>> getPath(){
        List<ApplicationVO> voList = applicationService.findAppCode();
        return Result.success(voList, "success");
    }

    @Override
    protected CommonService<Application> getService() {
        return applicationService;
    }

}
