package com.allinabc.cloud.activiti.adapt;

import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/24 11:02
 **/
public interface FormPermissionAdapt {
    /**
     * 流程启动执行保存权限
     */

    void saveStartFormPerms(String formType, String businessId, String accountType, String userId, Map<String, Object> variables,String taskDefKey,String procDefKey);


    /**
     * 流程执行保存权限
     */
    void saveExecuteFormPerms(String formType, String businessId, String accountType, String userId, Map<String, Object> variables,String taskDefKey,String procDefKey,String startUser);



}
