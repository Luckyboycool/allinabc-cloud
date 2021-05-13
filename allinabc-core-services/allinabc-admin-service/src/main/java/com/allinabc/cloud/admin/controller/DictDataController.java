package com.allinabc.cloud.admin.controller;

import com.allinabc.cloud.admin.pojo.dto.DictDateParam;
import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.admin.service.DictDataService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  接口迁移
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
@Api(tags = "字典数据")
@RestController
@RequestMapping("/dict/data")
@BizClassification(serviceName = "admin",modelName = "dict",bizName = "字典数据")
public class DictDataController extends MybatisBaseCrudController<DictData> {

    @Autowired
    private DictDataService dictDataService;

    @GetMapping("/get/label/{dictType}/{dictValue}")
    public Result<String> getLabel(@PathVariable String dictType, @PathVariable String dictValue) {
        String label = dictDataService.selectDictLabel(dictType, dictValue);
        return Result.success(label,"success");
    }

    @GetMapping("/list/type/{dictType}")
    public Result<List<DictData>> getByType(@PathVariable String dictType) {
        List<DictData> list = dictDataService.selectByDictType(dictType);
        return Result.success(list, "查询成功");
    }

    @GetMapping("/get-list")
    public Result<List<DictData>> getDictValueByTypeAndCode(@RequestParam String dictType, @RequestParam String appCode) {
        List<DictData> list = dictDataService.getDictValueByTypeAndCode(dictType, appCode);
        return Result.success(list, "查询成功");
    }

    @PostMapping("/list/filter")
    public Result getDictValueByFilter(@RequestBody DictDateParam dictDateParam) {
        return  dictDataService.getDictValueByFilter(dictDateParam);
    }

    @Override
    protected CommonService<DictData> getService() {
        return dictDataService;
    }

}
