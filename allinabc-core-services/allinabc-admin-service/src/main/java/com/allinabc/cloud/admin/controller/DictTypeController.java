package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.po.DictType;
import com.allinabc.cloud.admin.service.DictTypeService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器（代码迁移改造）
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
@Api(tags = "字典类型")
@RestController
@RequestMapping("/dict/type")
@BizClassification(serviceName = "admin",modelName = "dict",bizName = "字典数据")
public class DictTypeController extends MybatisBaseCrudController<DictType> {

    @Autowired
    private DictTypeService dictTypeService;

    @Override
    protected CommonService<DictType> getService() {
        return dictTypeService;
    }

}
