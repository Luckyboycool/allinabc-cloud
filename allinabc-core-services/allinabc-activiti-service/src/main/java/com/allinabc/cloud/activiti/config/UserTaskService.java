package com.allinabc.cloud.activiti.config;

import com.allinabc.cloud.activiti.service.impl.GtaAsyncTask;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.enums.BasicFormStatus;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/1 15:29
 **/
@Slf4j
@Component
public class UserTaskService implements JavaDelegate {

    private Expression rest;

//    @Resource
//    private BizFormHandler handler;

    @Resource
    private GtaAsyncTask gtaAsyncTask;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("UserTaskService调用了；*********************************流程结束！"+rest);
        gtaAsyncTask.updateBasicFormStatus(execution.getProcessInstanceId());
        // 根据InstanceId找到BasicInfoId
        // 将BasicForm中的Status设置为Completed
        // 调用Notice Api发送流程结束通知
        //handler.processEnd();


    }
}
