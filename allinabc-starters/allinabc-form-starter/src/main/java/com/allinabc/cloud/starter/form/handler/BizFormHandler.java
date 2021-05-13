package com.allinabc.cloud.starter.form.handler;

import cn.hutool.core.bean.BeanUtil;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.starter.form.param.BizFormParam;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BizFormHandler {

    public void beforeFormSave(BizFormParam param) {
        log.info("beforeFormSave executed!");
    }

    public void validateForm(BizFormParam param) {
    }

    public void afterFormSave(BizFormParam param) {
        log.info("afterFormSave executed!");
    }

    public void beforeCustomFormSave(BizFormParam param) {
        log.info("beforeCustomFormSave executed!");
    }

    public abstract void saveCustomForm(BizFormParam param);

    public void afterCustomFormSave(BizFormParam param) {
        log.info("afterCustomFormSave executed!");
    }

    public void beforeStartProcess(BizFormParam param) {
        log.info("beforeStartProcess executed!");
    }

    public void afterStartProcess(BizFormParam param,String instanceId) {
        log.info("afterStartProcess executed!");
    }

    public void beforeExecuteProcess(BizFormParam param) {
        log.info("beforeExecuteProcess executed!");
    }

    public void afterExecuteProcess(BizFormParam param, List<String> taskIds){
        log.info("afterExecuteProcess executed!");
    }

    /**
     * 构建流程引擎参数集
     * @return
     */
    public Map<String, Object> buildProcessPayload(BizFormParam param) {
        Map<String, Object> payloadMap = Maps.newHashMap();

        // 构建通用Payload
        if(param.getAudit()!=null) {
            payloadMap.putAll(BeanUtil.beanToMap(param.getAudit()));
            if(null!=param.getAudit().getVariables()){
                payloadMap.putAll(param.getAudit().getVariables());
            }

        }
        if(param.getForm()!=null){
            HashMap<String, Object> hs = Maps.newHashMap();
            hs.put("basicInfoId",param.getForm().getId());
            hs.put("requestNo",param.getForm().getRequestNo());
            hs.put("subject",param.getForm().getSubject());
            hs.put("requester",param.getForm().getRequester());
            hs.put("processDefKey",param.getForm().getProcessId());
            hs.put("formType",param.getForm().getFormType());
            payloadMap.putAll(hs);
        }
        // 构建客户自己的Payload
        Map<String, Object> customPayLoad = buildCustomPayload(param);
        if(customPayLoad!=null) {
            payloadMap.putAll(customPayLoad);
        }

        return payloadMap;
    }

    /**
     * 构建流程引擎参数
     * @param param
     * @return
     */
    public Map<String, Object> buildCustomPayload(BizFormParam param) {
        return null;
    }

    /**
     * 流程结束回调方法
     */
    public void processEnd(){
        System.out.println("流程结束回调方法");
        // TODO 流程完成业务
        // 1. 将当前表单的状态置为Completed
        // 2. 如果当前表单有父表单，且父表单的状态为被改版，则将父表单的状态改为废版本

        // TODO 取消流程需要考虑恢复父版状态
    }

    public void beforeFormSubmit(BizFormParam param){

    }

    public void afterFormSubmit(BizFormParam param) {

    }

    public abstract User getCurrentUser();


    public abstract String getRequestNo(BizFormParam param);

}
