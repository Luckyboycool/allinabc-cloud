//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.BizLeave;
//import com.allinabc.cloud.activiti.service.IBizBusinessService;
//import com.allinabc.cloud.activiti.service.IBizLeaveService;
////import com.allinabc.cloud.admin.api.service.api.ApiUserService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 请假 提供者
// *
// * @author ruoyi
// * @date 2020-01-07
// */
//@RestController
//@RequestMapping("leave")
//public class BizLeaveController extends MybatisBaseCrudController<BizLeave> {
//
//    @Autowired
//    private IBizLeaveService    leaveService;
//    @Autowired
//    private IBizBusinessService bizBusinessService;
////    @Autowired
////    private ApiUserService      apiUserService;
//
////    @GetMapping("biz/{businessKey}")
////    public Result<BizLeave> biz(@PathVariable("businessKey") String businessKey) {
////        BizBusiness business = bizBusinessService.selectById(businessKey);
////        if (null != business) {
////            BizLeave leave = leaveService.selectBizLeaveById(business.getTableId());
////            return Result.success(leave);
////        }
////        return Result.failed("no record");
////    }
////
////    /**
////     * 新增保存请假
////     */
////    @PostMapping("save")
////    public Result<Void> addSave(@RequestBody BizLeave leave) {
////        int index = leaveService.insertBizLeave(leave);
////        if (index == 1) {
////            BizBusiness business = initBusiness(leave);
////            bizBusinessService.insert(business);
////            Map<String, Object> variables = Maps.newHashMap();
////            // 这里可以设置各个负责人，key跟模型的代理变量一致
////            // variables.put("pm", 1l);
////            // variables.put("sup", 1l);
////            // variables.put("gm", 1l);
////            // variables.put("hr", 1l);
////            variables.put("duration", leave.getDuration());
////            bizBusinessService.startProcess(business, variables);
////        }
////        return Result.success();
////    }
////
////    /**
////     *
////     * @param leave
////     * @return
////     * @author zmr
////     */
////    private BizBusiness initBusiness(BizLeave leave)
////    {
////        BizBusiness business = new BizBusiness();
////        business.setTableId(leave.getId().toString());
////        business.setProcDefId(leave.getProcDefId());
////        business.setTitle(leave.getTitle());
////        business.setProcName(leave.getProcName());
////        User currentUser = CurrentUserUtil.getCurrentUser();
////        business.setApplyer(currentUser.getUserName());
////        business.setStatus(ActivitiConstant.STATUS_DEALING);
////        business.setResult(ActivitiConstant.RESULT_DEALING);
////        business.setApplyTime(new Date());
////        return business;
////    }
//
//
//    @Override
//    protected CommonService<BizLeave> getService() {
//        return leaveService;
//    }
//
//}
