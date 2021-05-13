package com.allinabc.cloud.starter.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.druid.util.StringUtils;
import com.allinabc.cloud.activiti.api.service.api.ApiProcessService;
import com.allinabc.cloud.activiti.pojo.params.BasicFormDTO;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.allinabc.cloud.attach.api.service.api.ApiAttachmentService;
import com.allinabc.cloud.attach.pojo.dto.AttachmentParam;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.starter.form.annotation.BizType;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.enums.BasicFormStatus;
import com.allinabc.cloud.starter.form.handler.BizFormHandler;
import com.allinabc.cloud.starter.form.handler.manager.BizFormHandlerManager;
import com.allinabc.cloud.starter.form.param.BasicFormParam;
import com.allinabc.cloud.starter.form.param.BizFormParam;
import com.allinabc.cloud.starter.form.service.BasicFormService;
import com.allinabc.cloud.starter.form.service.BizFormService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BizFormServiceImpl implements BizFormService {

    @Resource
    private BasicFormService basicFormService;
    @Resource
    private ApiProcessService apiProcessService;
    @Resource
    private ApiAttachmentService apiAttachmentService;

    @Transactional(rollbackFor = Exception.class)
    public void operate(BizFormParam param) {
        BizType bizAnnotation = param.getClass().getAnnotation(BizType.class);
        Assert.notNull(bizAnnotation, "BizFormParam should add BizType annotation ");
        String bizType = bizAnnotation.value();
        Assert.notNull(bizType, "Biz Type should not be null ");
        BizFormHandler handler = BizFormHandlerManager.get(bizType);
        Assert.notNull(bizType, "BizFormHandler should not be null with type: " + bizType);

        log.debug("begin clone and save basic form info ");
        handler.beforeFormSave(param);
        handler.validateForm(param);

        BasicForm basicForm = BeanUtil.copyProperties(param.getForm(), BasicForm.class);
        User loginUser = handler.getCurrentUser();
        basicForm.setUserType(loginUser.getAccountType());
        if (param.isCreate()) {
            String basicInfoId = IdUtil.createSnowflake(1, 1).nextIdStr();
            basicForm.setId(basicInfoId);
            basicForm.setIsDraft("Y");
            basicForm.setStatus(BasicFormStatus.DRAFT.getCode());
            basicForm.setRequester(loginUser.getId());
            Date date = new Date();
            basicForm.setRequestTime(date);
            basicForm.setCreateTm(date);
            basicForm.setCreatedBy(loginUser.getId());
            if (Strings.isNullOrEmpty(basicForm.getRequestNo())) {
                basicForm.setRequestNo(handler.getRequestNo(param));
            }
        } else {
            basicForm.setUpdatedBy(loginUser.getId());
            basicForm.setUpdateTm(new Date());
        }
        // TODO DTO 转 PO 带节点写权限过滤
        // TODO 递归塞值，将BizFormParam中的子BizFormParam属性的createType和actionType值塞入
        BasicFormParam basicFormParam = BeanUtil.copyProperties(basicForm, BasicFormParam.class);
        param.setForm(basicFormParam);
        if (!StringUtils.equals(param.getCreateType(), "AUDIT")) {
            basicFormService.saveOrUpdate(basicForm);
        }
        handler.afterFormSave(param);
        log.debug("ending clone and save basic form info ");

        log.debug("begin clone and save attachment info ");
        List<AttachmentParam> ls = Lists.newArrayList();
        if (null != param.getAttachmentIds() && param.getAttachmentIds().size() > 0) {
            param.getAttachmentIds().forEach(id -> {
                AttachmentParam attachment = new AttachmentParam();
                attachment.setBizId(basicForm.getId());
                attachment.setId(id);
                ls.add(attachment);
            });
            BizAttachmentListParam bizAttachmentListParam = new BizAttachmentListParam();
            bizAttachmentListParam.setAttachmentParams(ls);
            apiAttachmentService.updateBizIds(bizAttachmentListParam);
        }
        log.debug("ending clone and save attachment info ");

        log.debug("begin clone and save custom form info ");
        handler.beforeCustomFormSave(param);
        handler.saveCustomForm(param);
        handler.afterCustomFormSave(param);
        log.debug("ending clone and save custom form info ");

        handler.beforeFormSubmit(param);
        if (param.isSubmit()) {
            // 1. 获取当前表单对应的表单类型有无流程
            // 2. 有流程则执行流程逻辑
            ProcessIdModel processIdModel = apiProcessService.findProcessIdByFormType(basicForm.getFormType());
            if (processIdModel != null && !StringUtils.isEmpty(processIdModel.getProcessId())) {
                BasicFormDTO basicFormDTO = BeanUtil.copyProperties(basicForm, BasicFormDTO.class);
                // 启动流程
                if (param.isDraft()) {
                    handler.beforeStartProcess(param);
                    ProcessStartParam processStartParam = new ProcessStartParam(processIdModel.getProcessId(), basicForm.getId(), basicForm.getRequester(), param.getAudit().getDecision(), handler.buildProcessPayload(param), basicFormDTO);
                    TaskModel taskModel = apiProcessService.startProcess(processStartParam);
                    // handler.assignNextNode(param, instance);
                    // handler.afterStartProcess(param, instance);
                    basicForm.setIsDraft("N");
                    basicForm.setProcessId(taskModel.getProcessDefinitionKey());
                    basicForm.setInstanceId(taskModel.getInstanceId());
                    basicFormService.saveOrUpdate(basicForm);
                    handler.afterStartProcess(param, taskModel.getInstanceId());
                    if (param.getActionType().contains("REVISION")) {
                        basicFormService.updateStatus(param.getForm().getPid(), BasicFormStatus.REDESIGN.getCode(), handler.getCurrentUser().getId());
                        //判断formType=mtr 拿pid作为子表单的mainId改成被改版
                        if(param.getForm().getFormType().equals("mtr")){
                            BasicForm var1 = new BasicForm();
                            var1.setMainId(param.getForm().getPid());
                            List<BasicForm> sonBasicForm = basicFormService.selectList(var1);
                            sonBasicForm.forEach(form->{
                                basicFormService.updateStatus(form.getId(), BasicFormStatus.REDESIGN.getCode(), handler.getCurrentUser().getId());
                            });
                        }
                    }
                    basicFormService.updateStatus(basicForm.getId(), BasicFormStatus.PROCESSING.getCode(), handler.getCurrentUser().getId());
                }
                //
                else {
                    handler.beforeExecuteProcess(param);
                    ProcessExecuteParam processExecuteParam = new ProcessExecuteParam(param.getAudit().getInstanceId(), param.getAudit().getTaskId(), param.getAudit().getDecision(), param.getAudit().getAuditId(), param.getForm().getId(), handler.buildProcessPayload(param), basicFormDTO);
                    List<String> taskId = apiProcessService.executeProcess(processExecuteParam);
                    // handler.assignNextNode(param, instance);
                    handler.afterExecuteProcess(param, taskId);
                    if(taskId!=null&&taskId.size()>0){
                        basicFormService.updateStatus(basicForm.getId(), BasicFormStatus.PROCESSING.getCode(), handler.getCurrentUser().getId());
                    }
                }
               // basicFormService.updateStatus(basicForm.getId(), BasicFormStatus.PROCESSING.getCode(), handler.getCurrentUser().getId());
            }

            // 3. 无流程，则将表单草稿状态修改为N，结束业务
            else {
                basicFormService.updateDraftStatus(basicForm.getId(), BasicFormStatus.COMPLETED.getCode(), "N", handler.getCurrentUser().getId());
                //
                if (param.getActionType().contains("REVISION")) {
                    basicFormService.updateStatus(param.getForm().getPid(), BasicFormStatus.WASTE_VERSION.getCode(), handler.getCurrentUser().getId());
                }
            }
        }
        handler.afterFormSubmit(param);
    }

}
