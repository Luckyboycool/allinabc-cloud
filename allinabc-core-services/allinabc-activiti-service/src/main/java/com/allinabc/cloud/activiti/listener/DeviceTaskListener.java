package com.allinabc.cloud.activiti.listener;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.notice.MailTaskService;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/14 21:58
 **/
@Component
@Slf4j
public class DeviceTaskListener implements TaskListener {

    @Autowired
    private MailTaskService mailTaskService;

    @Override
    public void notify(DelegateTask delegateTask) {
        try {
            Map<String, Object> variables = delegateTask.getVariables();
            log.info("获取节点的流程变量" + JSON.toJSONString(variables));
            String eventName = delegateTask.getEventName();
            String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
            String processInstanceId = delegateTask.getProcessInstanceId();
            String requester = (String)variables.get("requester");
            if ("create".endsWith(eventName)) {
                if(ProcessNodeConstant.DEVICE_CE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.DEVICE_CE_CONFIRM+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);
                }

                if(ProcessNodeConstant.DEVICE_PIE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.DEVICE_PIE_CONFIRM+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);
                }
                if(ProcessNodeConstant.DEVICE_DS_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.DEVICE_DS_CONFIRM+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);
                }


            } else if ("assignment".endsWith(eventName)) {
                log.info("节点设置审批人事件");
            } else if ("complete".endsWith(eventName)) {
                if(ProcessNodeConstant.DEVICE_CE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.DEVICE_CE_CONFIRM+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("N".equals(applyFlag)){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);
                    }
                }
                if(ProcessNodeConstant.DEVICE_PIE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.DEVICE_PIE_CONFIRM+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("Y".equals(applyFlag)){
                        //完成
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_COMPLETE,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);
                    }else if("N".equals(applyFlag)){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);

                    }
                }

                if(ProcessNodeConstant.DEVICE_DS_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.DEVICE_DS_CONFIRM+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("Y".equals(applyFlag)){
                        //完成
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_COMPLETE,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);
                    }else if("N".equals(applyFlag)){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.DEVICE_PROCESS_DEF_KEY,requester);

                    }
                }

            } else if ("delete".endsWith(eventName)) {
                log.info("节点删除事件");

            }
        }catch (Exception e){
            log.error("通知时间出错了");
        }
    }

}
