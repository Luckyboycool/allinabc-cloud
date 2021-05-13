package com.allinabc.cloud.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.FormPermissionAdapt;
import com.allinabc.cloud.activiti.adaptimpl.permission.*;
import com.allinabc.cloud.activiti.mapper.FormPermissionMapper;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.activiti.service.FormPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/23 19:42
 **/
@Service
@Slf4j
public class FormPermissionServiceImpl implements FormPermissionService {

    @Autowired
    private ToptierFormPermissionService toptierFormPermissionService;

    @Autowired
    private TapeoutFormPermissionService tapeoutFormPermissionService;

    @Autowired
    private DeviceFormPermissionService deviceFormPermissionService;

    @Autowired
    private IpMergeFormPermissionService ipMergeFormPermissionService;

    @Autowired
    private MtrFormPermissionService mtrFormPermissionService;
    @Resource
    private FormPermissionMapper formPermissionMapper;

    @Override
    public void savePermission(Boolean isStartProcess, String businessId, String accountType, String userId, Map<String, Object> variables, String taskDefKey, String procDefKey,String startUserId) {
        log.info("存取权限参数:accountType" + accountType + ";userId=" + userId + " businessId=" + businessId + ";variables=" + JSON.toJSONString(variables));
        String formType = (String) variables.get("formType");
        if (StringUtils.isEmpty(formType)) {
            throw new RuntimeException("表单类型数据为空");
        }
        //描述：判断是否是启动流程
        if (isStartProcess) {
            //如果是启动流程
            log.info("启动流程保存权限");
            FormPermissionAdapt formPermissionAdapt = null;
            switch (formType) {
                case ProcessNodeConstant.FORM_TYPE_TOPTIER:
                    formPermissionAdapt = toptierFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_TAPEOUT:
                    formPermissionAdapt = tapeoutFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_DEVICE:
                    formPermissionAdapt = deviceFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_IPMERGE:
                    formPermissionAdapt = ipMergeFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_MTR:
                    formPermissionAdapt = mtrFormPermissionService;
                    break;
                default:
                    break;
            }
            formPermissionAdapt.saveStartFormPerms(formType, businessId, accountType, userId, variables, taskDefKey, procDefKey);
        } else {
            //如果是执行流程
            log.info("执行流程保存权限");
            FormPermissionAdapt formPermissionAdapt = null;
            switch (formType) {
                case ProcessNodeConstant.FORM_TYPE_TOPTIER:
                    formPermissionAdapt = toptierFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_TAPEOUT:
                    formPermissionAdapt = tapeoutFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_DEVICE:
                    formPermissionAdapt = deviceFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_IPMERGE:
                    formPermissionAdapt = ipMergeFormPermissionService;
                    break;
                case ProcessNodeConstant.FORM_TYPE_MTR:
                    formPermissionAdapt = mtrFormPermissionService;
                    break;
                default:
                    break;
            }
            formPermissionAdapt.saveExecuteFormPerms(formType, businessId, accountType, userId, variables, taskDefKey, procDefKey,startUserId);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void addPermission(FormPermission formPermission) {
        formPermissionMapper.insert(formPermission);
    }
}
