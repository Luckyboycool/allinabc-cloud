package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.po.FormPermission;

import java.util.Map;

/**
 * @Description 当前表单的额权限
 * @Author wangtaifeng
 * @Date 2021/3/23 19:42
 **/
public interface FormPermissionService {

    /**
     *
     * @param isStartProcess 是否是启动流程
     * @param accountType
     * @param userId
     * @param variables
     */
    void savePermission(Boolean isStartProcess,String businessId,String accountType,String userId,Map<String, Object> variables,String taskDefKey,String procDefKey,String startUserId);
    void addPermission(FormPermission formPermission);
}
