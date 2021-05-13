//package com.allinabc.cloud.activiti.notice.impl;
//
//import com.allinabc.cloud.activiti.notice.MailTaskService;
//import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
//import com.allinabc.cloud.notice.pojo.constant.NoticeConstant;
//import com.gta.cloud.pojo.tapeout.params.mail.MailParam;
//import com.gta.cloud.tapeout.api.service.api.ApiTapeoutService;
//import lombok.extern.slf4j.Slf4j;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
///**
// * @Description
// * @Author wangtaifeng
// * @Date 2021/3/25 19:44
// **/
//@Service
//@Slf4j
//public class ToptierMailTaskService implements MailTaskService {
//
//    @Autowired
//    private RuntimeService runtimeService;
//
//
//    @Autowired
//    private ApiTapeoutService apiTapeoutService;
//
//    /**
//     * 异步发送任务
//     * @param variables
//     * @param processInstanceId
//     * @param taskDefinitionKey
//     */
//    @Async
//    public void sendMail(Map<String, Object> variables, String processInstanceId, String taskDefinitionKey) {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Object obj = variables.get("applyFlag");
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        log.info("异步执行发送邮件");
//        if (taskDefinitionKey.equals(ProcessNodeConstant.TOPTIER_CREATE)) {
//            log.info("节点=" + ProcessNodeConstant.TOPTIER_CREATE + " 完成。。。。");
//            if(obj!=null){
//                String applyFlag = (String)obj;
//                if(applyFlag.equals("Y")){
//                    //那么发送待办通知
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_APPLY_LEADER);
//                    mailParam.setRequester((String)variables.get("requester"));
//                    apiTapeoutService.sendMail(mailParam);
//                }
//            }
//        } else if (taskDefinitionKey.equals(ProcessNodeConstant.TOPTIER_APPLY_LEADER)) {
//            log.info("节点=" + ProcessNodeConstant.TOPTIER_APPLY_LEADER + " 完成。。。。");
//            if(obj!=null){
//                String applyFlag = (String)obj;
//                if(applyFlag.equals("Y")){
//                    //那么发送待办通知
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_MASK);
//                    mailParam.setRequester(processInstance.getStartUserId());
//                    apiTapeoutService.sendMail(mailParam);
//                }else if(applyFlag.equals("N")){
//                    //驳回
//                    log.info("发送驳回通知");
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_GIVE_BACK);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_CREATE);
//                    mailParam.setRequester(processInstance.getStartUserId());
//                    apiTapeoutService.sendMail(mailParam);
//                }
//            }
//
//        } else if (taskDefinitionKey.equals(ProcessNodeConstant.TOPTIER_MASK)) {
//            log.info("节点=" + ProcessNodeConstant.TOPTIER_MASK + " 完成。。。。");
//            if(obj!=null){
//                String applyFlag = (String)obj;
//                if(applyFlag.equals("Y")){
//                    //那么发送待办通知
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_MASK_LEADER);
//                    mailParam.setRequester(processInstance.getStartUserId());
//                    apiTapeoutService.sendMail(mailParam);
//                }else if(applyFlag.equals("N")){
//                    //驳回
//                    log.info("发送驳回通知");
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_GIVE_BACK);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_CREATE);
//                    mailParam.setRequester(processInstance.getStartUserId());
//                    apiTapeoutService.sendMail(mailParam);
//                }
//            }
//
//        } else if (taskDefinitionKey.equals(ProcessNodeConstant.TOPTIER_MASK_LEADER)) {
//            log.info("节点=" + ProcessNodeConstant.TOPTIER_MASK_LEADER + " 完成。。。。");
//            if(obj!=null){
//                String applyFlag = (String)obj;
//                if(applyFlag.equals("Y")){
//                    log.info("发送完成通知");
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_COMPLETE);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_CREATE);
//                    mailParam.setRequester(processInstance.getStartUserId());
//                    apiTapeoutService.sendMail(mailParam);
//                }else if(applyFlag.equals("N")){
//                    //驳回
//                    log.info("发送驳回通知");
//                    MailParam mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_GIVE_BACK);
//                    mailParam.setProcInstId(processInstanceId);
//                    mailParam.setProcDefkey(ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//                    mailParam.setTaskDefKey(ProcessNodeConstant.TOPTIER_CREATE);
//                    mailParam.setRequester(processInstance.getStartUserId());
//                    apiTapeoutService.sendMail(mailParam);
//                }
//            }
//        } else {
//            log.error("未找到对应节点");
//        }
//
//
//    }
//
//
//
//    public MailParam setMailContentAndSubject(String noticeTypeCode){
//        MailParam mailRequest = new MailParam();
//        mailRequest.setIsHtml(false);
//        mailRequest.setNeedRetry(false);
//        mailRequest.setNoticeWay(NoticeConstant.NOTICE_WAY_ZERO);
//        mailRequest.setNoticeTypeCode(noticeTypeCode);
//        return mailRequest;
//    }
//}
