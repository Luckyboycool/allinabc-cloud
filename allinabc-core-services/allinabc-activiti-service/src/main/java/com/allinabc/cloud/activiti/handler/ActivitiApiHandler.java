//package com.allinabc.cloud.activiti.handler;
//
//import com.allinabc.cloud.starter.form.handler.BizFormHandler;
//import org.activiti.engine.delegate.DelegateExecution;
//import org.activiti.engine.delegate.JavaDelegate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//
//@Component
//public class ActivitiApiHandler implements JavaDelegate {
//
//    @Resource
//    private BizFormHandler handler;
//
//    @Override
//    public void execute(DelegateExecution execution) {
//        // 根据InstanceId找到BasicInfoId
//        // 将BasicForm中的Status设置为Completed
//        // 调用Notice Api发送流程结束通知
//        handler.processEnd();
//    }
//
//}
