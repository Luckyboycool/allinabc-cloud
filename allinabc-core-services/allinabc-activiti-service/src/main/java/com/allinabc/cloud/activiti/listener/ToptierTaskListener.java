package com.allinabc.cloud.activiti.listener;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.notice.MailTaskService;
import com.allinabc.cloud.activiti.pojo.bo.MailBO;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.activiti.pojo.po.NodeAuditor;
import com.allinabc.cloud.activiti.service.impl.GtaAsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/14 21:58
 **/
@Component
@Slf4j
public class ToptierTaskListener implements TaskListener {

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
            if(StringUtils.isEmpty(requester)){
                throw new RuntimeException("申请人获取为空");
            }
            if ("create".endsWith(eventName)) {
                if(ProcessNodeConstant.TOPTIER_APPLY_LEADER.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.TOPTIER_APPLY_LEADER+"节点创建事件");
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);
                }
                if(ProcessNodeConstant.TOPTIER_MASK.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.TOPTIER_MASK+"节点创建事件");
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);
                }
                if(ProcessNodeConstant.TOPTIER_MASK_LEADER.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.TOPTIER_MASK_LEADER+"节点创建事件");
                    mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_PENDING_TRIAL,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);
                }
            } else if ("assignment".endsWith(eventName)) {
                log.info("节点设置审批人");
            } else if ("complete".endsWith(eventName)) {
                log.info("节点完成");
                if(ProcessNodeConstant.TOPTIER_APPLY_LEADER.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.TOPTIER_APPLY_LEADER+"节点完成事件");
                    String applyFlag =(String)variables.get("applyFlag");
                    if(applyFlag.equals("N")){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);

                    }
                }
                if(ProcessNodeConstant.TOPTIER_MASK.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.TOPTIER_MASK+"节点完成事件");
                    String applyFlag =(String)variables.get("applyFlag");
                    if(applyFlag.equals("N")){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);

                    }
                }
                if(ProcessNodeConstant.TOPTIER_MASK_LEADER.equals(taskDefinitionKey)){
                    log.info(ProcessNodeConstant.TOPTIER_MASK_LEADER+"节点完成事件");
                    String applyFlag =(String)variables.get("applyFlag");
                    if(applyFlag.equals("N")){
                        //驳回
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_GIVE_BACK,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);
                    }else if(applyFlag.equals("Y")){
                        mailTaskService.sendMail(ProcessNodeConstant.GTA_FORM_COMPLETE,variables,processInstanceId,taskDefinitionKey,ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY,requester);
                    }
                }

            } else if ("delete".endsWith(eventName)) {
                log.info("节点删除");

            }
        }catch (Exception e){
            log.error("通知时间出错了");
        }
    }

}
