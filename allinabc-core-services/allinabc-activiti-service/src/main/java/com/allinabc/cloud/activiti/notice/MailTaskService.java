package com.allinabc.cloud.activiti.notice;

import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/25 19:43
 **/
public interface MailTaskService {

    /**
     * 发送邮件
     * @param variables
     * @param processInstanceId
     * @param taskDefinitionKey
     */
     void sendMail(String noticeType,Map<String, Object> variables, String processInstanceId, String taskDefinitionKey,String procDefKey,String requester);
}
