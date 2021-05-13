//package com.allinabc.cloud.starter.form.handler;
//
//import com.allinabc.cloud.common.core.domain.User;
//import com.allinabc.cloud.starter.form.annotation.BizType;
//import com.allinabc.cloud.starter.form.param.BizFormParam;
//import com.allinabc.cloud.starter.form.param.ChildBizFormParam;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@BizType("child")
//public class ChildBizFormHandler extends BizFormHandler{
//
//    @Override
//    public void beforeFormSave(BizFormParam param) {
//        log.info("use custom child biz form handler...");
//        super.beforeFormSave(param);
//    }
//
//    @Override
//    public void saveCustomForm(BizFormParam param) {
//
//    }
//
//    @Override
//    public User getCurrentUser() {
//        return null;
//    }
//
//    @Override
//    public String getRequestNo(BizFormParam param) {
//        return null;
//    }
//}
