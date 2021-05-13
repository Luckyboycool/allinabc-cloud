//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.BizBusiness;
//import com.allinabc.cloud.activiti.service.IBizBusinessService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("business")
//public class BizBusinessController extends MybatisBaseCrudController<BizBusiness> {
//
//    @Autowired
//    private IBizBusinessService bizBusinessService;
//
////    /**
////     * 查询流程业务列表
////     */
////    @GetMapping("list/my")
////    public Result list(BizBusiness bizBusiness) {
////        bizBusiness.setUserId(CurrentUserUtil.getCurrentUserId());
////        bizBusiness.setDelFlag(false);
////        return Result.success(bizBusinessService.selectList(bizBusiness));
////    }
//
//    @Override
//    protected CommonService<BizBusiness> getService() {
//        return bizBusinessService;
//    }
//
//}
