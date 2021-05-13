package com.allinabc.cloud.starter.form.controller;

import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.form.domain.FormTableDef;
import com.allinabc.cloud.starter.form.service.FormTableDefService;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:34 下午
 **/
@Api(tags = "表单定义管理")
@RequestMapping("/form_table_def")
@RestController
public class FormTableDefController extends MybatisBaseCrudController<FormTableDef> {

    @Autowired
    private FormTableDefService formTableDefService;

    @ApiOperation(value = "查询表单下所有字段 是否可写")
    @GetMapping("/list_by_form_type")
    public Result<Map<String, Map<String, Map<String, String>>>> listByFormType(@RequestParam("formType") String formType,
                                                                                @RequestParam("nodeKey") String nodeKey) {
        return formTableDefService.listByFormType(formType, nodeKey);
    }

    @Override
    protected CommonService<FormTableDef> getService() {
        return formTableDefService;
    }
}
