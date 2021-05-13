package com.allinabc.cloud.activiti.notice.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.notice.MailTaskService;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.notice.pojo.constant.NoticeConstant;
import com.gta.cloud.pojo.tapeout.params.mail.MailParam;
import com.gta.cloud.tapeout.api.service.api.ApiTapeoutService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/25 19:44
 **/
@Service
@Slf4j
public class MailTaskServiceImpl implements MailTaskService {


    @Autowired
    private ApiTapeoutService apiTapeoutService;

    /**
     * 异步发送任务
     * @param variables
     * @param processInstanceId
     * @param taskDefinitionKey
     */
    @Async
    public void sendMail(String noticeType,Map<String, Object> variables, String processInstanceId, String taskDefinitionKey,String procDefKey,String requester) {
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // Object obj = variables.get("applyFlag");
        //ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        Date now = new Date();
//        Date date = DateUtil.offset(now, DateField.MINUTE, 1);
//        System.out.println(date);
//        int year = DateUtil.year(date);
//        int month = DateUtil.month(date)+1;
//        int day = DateUtil.dayOfMonth(date);
//        int hour = DateUtil.hour(date, true);
//        int minute = DateUtil.minute(date);
//        int second = DateUtil.second(date);
//        String timeCron = second+" "+minute+" "+hour+" "+day+" "+month+" "+"? "+year;
//        log.info("定时通知时间表达式="+timeCron);
//        CronUtil.schedule(timeCron, (Task) () -> {
//            log.info("延时一分钟任务邮件发送任务启动,启动cron表达式="+timeCron);
//        });
        MailParam mailParam = null;
        if(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL.equals(noticeType)){
            //待审
            mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,procDefKey,requester);
            log.info("异步执行待审通知邮件，请求参数="+JSON.toJSONString(mailParam));
        }else if(ProcessNodeConstant.GTA_FORM_GIVE_BACK.equals(noticeType)){
            //退回
            mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,procDefKey,requester);
            log.info("异步执行退回通知邮件,请求参数="+JSON.toJSONString(mailParam));
        }else if(ProcessNodeConstant.GTA_FORM_COMPLETE.equals(noticeType)){
            //完成
            mailParam = setMailContentAndSubject(ProcessNodeConstant.GTA_FORM_COMPLETE,variables,processInstanceId,taskDefinitionKey,procDefKey,requester);
            log.info("异步执行完成通知邮件,请求参数="+JSON.toJSONString(mailParam));
        }else{
            log.error("未找到该类型");
        }
        apiTapeoutService.sendMail(mailParam);

    }



    public MailParam setMailContentAndSubject(String noticeTypeCode,Map<String, Object> variables,String processInstanceId, String taskDefinitionKey,String procDefKey,String requester){
        MailParam mailRequest = new MailParam();
        mailRequest.setIsHtml(false);
        mailRequest.setNeedRetry(false);
        mailRequest.setNoticeWay(NoticeConstant.NOTICE_WAY_ZERO);
        mailRequest.setNoticeTypeCode(noticeTypeCode);
        mailRequest.setVariables(variables);
        mailRequest.setProcInstId(processInstanceId);
        mailRequest.setProcDefkey(procDefKey);
        mailRequest.setTaskDefKey(taskDefinitionKey);
        mailRequest.setRequester(requester);
        return mailRequest;
    }
}
