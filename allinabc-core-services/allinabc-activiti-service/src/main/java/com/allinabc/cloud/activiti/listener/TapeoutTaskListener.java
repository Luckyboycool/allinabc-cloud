package com.allinabc.cloud.activiti.listener;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.notice.MailTaskService;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.activiti.service.impl.GtaAsyncTask;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/14 21:58
 **/
@Component
@Slf4j
public class TapeoutTaskListener implements TaskListener {

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
                if(ProcessNodeConstant.IFAB_TAPEOUT_PIE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_PIE_CONFIRM+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                }

                if(ProcessNodeConstant.IFAB_TAPEOUT_MASK.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_MASK+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                }

                if(ProcessNodeConstant.IFAB_TAPEOUT_DATA_PIE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_DATA_PIE_CONFIRM+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                }

                if(ProcessNodeConstant.IFAB_TAPEOUT_DRC_CHECK.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_DRC_CHECK+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                }

                if(ProcessNodeConstant.IFAB_TAPEOUT_DATA_HANDLE.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_DATA_HANDLE+"节点创建");
                    //发送待办信息
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                }

            } else if ("assignment".endsWith(eventName)) {
                log.info("节点设置审批人事件");
            } else if ("complete".endsWith(eventName)) {

                if(ProcessNodeConstant.IFAB_TAPEOUT_MASK.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_MASK+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("N".equals(applyFlag)){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                    }
                }

                if(ProcessNodeConstant.IFAB_TAPEOUT_DRC_CHECK.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_DRC_CHECK+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("N".equals(applyFlag)){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                    }
                }


                if(ProcessNodeConstant.IFAB_TAPEOUT_DATA_HANDLE.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_DATA_HANDLE+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("N".equals(applyFlag)){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
                    }
                }

                if(ProcessNodeConstant.IFAB_TAPEOUT_DATA_PIE_CONFIRM.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.IFAB_TAPEOUT_DATA_PIE_CONFIRM+"节点完成");
                    //需要判断是同意还是退回
                    String applyFlag =(String)variables.get("applyFlag");
                    if("Y".equals(applyFlag)){
                        //同意
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_COMPLETE,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.IFAB_TAPEOUT_PROCESS,requester);
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
