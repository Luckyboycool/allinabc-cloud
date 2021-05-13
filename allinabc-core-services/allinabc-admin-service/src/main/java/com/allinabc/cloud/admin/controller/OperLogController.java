package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.dto.OperLogDTO;
import com.allinabc.cloud.admin.pojo.po.OperLog;
import com.allinabc.cloud.admin.service.OperLogService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2020-12-16
 */
@Api(tags = "操作日志")
@RestController
@RequestMapping("/operLog")
@BizClassification(serviceName = "monitor",modelName = "operlog",bizName = "操作日志")
public class OperLogController extends MybatisBaseCrudController<OperLog> {

    @Autowired
    private OperLogService operLogService;

    @PostMapping("/add")
    public Result<Void> insertOperLog(@RequestBody OperLogDTO operLog) {
        OperLog entity = new OperLog();
        BeanUtils.copyProperties(entity,operLog);
        operLogService.save(entity);
        return Result.success();
    }

    @Override
    protected CommonService<OperLog> getService() {
        return operLogService;
    }

}
