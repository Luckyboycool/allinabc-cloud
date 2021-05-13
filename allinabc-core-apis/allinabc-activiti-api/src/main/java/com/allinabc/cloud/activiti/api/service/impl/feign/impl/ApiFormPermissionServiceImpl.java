package com.allinabc.cloud.activiti.api.service.impl.feign.impl;

import com.allinabc.cloud.activiti.api.service.api.ApiFormPermissionService;
import com.allinabc.cloud.activiti.api.service.api.ApiProcessService;
import com.allinabc.cloud.activiti.api.service.impl.feign.client.FormPermissionServiceApi;
import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiFormPermissionServiceImpl implements ApiFormPermissionService {
    @Autowired
    private FormPermissionServiceApi formPermissionServiceApi;

    @Override
    public void addPermission(FormPermission formPermission) {
        Result<Void> result = formPermissionServiceApi.addPermission(formPermission);
        if(result.getCode()!=20000){
            throw new BusinessException("调用服务失败");
        }
    }
}
