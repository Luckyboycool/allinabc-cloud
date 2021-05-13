//package com.allinabc.cloud.starter.form.controller;
//
//import com.allinabc.cloud.common.web.pojo.resp.Result;
//import com.allinabc.cloud.starter.form.param.ChildBizFormParam;
//import com.allinabc.cloud.starter.form.service.BizFormService;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/child")
//public class ChildBizFormController {
//
//    @Resource
//    private BizFormService bizFormService;
//
//    public Result<Void> saveAndSubmit(@RequestBody ChildBizFormParam param) {
//        bizFormService.execute(param);
//        return Result.success();
//    }
//
//}
