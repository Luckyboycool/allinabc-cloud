package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.po.Config;
import com.allinabc.cloud.admin.pojo.vo.SysConfigVO;
import com.allinabc.cloud.admin.service.ConfigService;
import com.allinabc.cloud.common.core.mvc.BaseController;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
@Api(tags = "系统配置")
@RestController
@RequestMapping("/config")
@BizClassification(serviceName = "admin",modelName = "config",bizName = "系统配置")
public class ConfigController extends BaseController<Config> {

    @Resource
    private ConfigService configService;

    @GetMapping("/get/key/{key}")
    public Result<Config> getByKey(@PathVariable String key) {
        Config entity = configService.selectByKey(key);
        return Result.success(entity);
    }

    @GetMapping("/get/key/{appCode}/{key}")
    public Result<Config> getByKeyAndApp(@PathVariable String appCode, @PathVariable String key) {
        Config entity = configService.selectByKeyAndApp(appCode, key);
        return Result.success(entity);
    }

    @GetMapping("/get/map/{appCode}")
    public Result<List<SysConfigVO>> getPathByKeyAndApp(@PathVariable String appCode) {
        List<SysConfigVO> voList = configService.selectMapConfigByAppCode(appCode);
        return Result.success(voList);
    }

    @Override
    protected CommonService<Config> getService() {
        return configService;
    }

}
