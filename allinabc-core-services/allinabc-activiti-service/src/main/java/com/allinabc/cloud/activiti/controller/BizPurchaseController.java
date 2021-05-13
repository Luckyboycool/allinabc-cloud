//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.BizPurchase;
//import com.allinabc.cloud.activiti.service.IBizBusinessService;
//import com.allinabc.cloud.activiti.service.IBizPurchaseService;
////import com.allinabc.cloud.admin.api.service.api.ApiUserService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 报销 提供者
// *
// * @author ruoyi
// * @date 2020-01-07
// */
//@RestController
//@RequestMapping("purchase")
//public class BizPurchaseController extends MybatisBaseCrudController<BizPurchase> {
//
//    @Autowired
//    private IBizPurchaseService purchaseService;
//    @Autowired
//    private IBizBusinessService bizBusinessService;
////    @Autowired
////    private ApiUserService      apiUserService;
////
////    /**
////     * 根据业务key获取数据
////     *
////     * @param businessKey
////     * @return
////     * @author zmr
////     */
////    @GetMapping("biz/{businessKey}")
////    public Result biz(@PathVariable("businessKey") String businessKey) {
////        BizBusiness business = bizBusinessService.selectById(businessKey);
////        if (null != business) {
////            BizPurchase purchase = purchaseService.selectBizPurchaseById(business.getTableId());
////            return Result.success(purchase);
////        }
////        return Result.failed("no record");
////    }
////
////    /**
////     * 新增保存报销
////     */
////    @PostMapping("save")
////    public Result addSave(@RequestBody BizPurchase purchase)
////    {
////        int index = purchaseService.insertBizPurchase(purchase);
////        if (index == 1)
////        {
////            BizBusiness business = initBusiness(purchase);
////            bizBusinessService.insert(business);
////            Map<String, Object> variables = Maps.newHashMap();
////            // 这里可以设置各个负责人，key跟模型的代理变量一致
////            // variables.put("fm", 1l);
////            // variables.put("fc", 1l);
////            // variables.put("gm", 1l);
////            variables.put("money", purchase.getMoney());
////            bizBusinessService.startProcess(business, variables);
////        }
////        return Result.success(index);
////    }
////
////    /**
////     * biz构造业务信息
////     * @param purchase
////     * @return
////     * @author zmr
////     */
////    private BizBusiness initBusiness(BizPurchase purchase) {
////        BizBusiness business = new BizBusiness();
////        business.setTableId(purchase.getId().toString());
////        business.setProcDefId(purchase.getProcDefId());
////        business.setTitle(purchase.getTitle());
////        business.setProcName(purchase.getProcName());
////
////        User currentUser = CurrentUserUtil.getCurrentUser();
////        business.setUserId(currentUser.getId());
////        business.setApplyer(currentUser.getUserName());
////        business.setStatus(ActivitiConstant.STATUS_DEALING);
////        business.setResult(ActivitiConstant.RESULT_DEALING);
////        business.setApplyTime(new Date());
////        return business;
////    }
//
//    @Override
//    protected CommonService<BizPurchase> getService() {
//        return purchaseService;
//    }
//
//}
