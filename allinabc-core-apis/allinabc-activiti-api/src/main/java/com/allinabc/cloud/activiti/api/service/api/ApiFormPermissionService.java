package com.allinabc.cloud.activiti.api.service.api;

import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/29 16:54
 **/
public interface ApiFormPermissionService {
    void addPermission(FormPermission formPermission);
}
