package com.allinabc.cloud.activiti.api.service.impl.feign.client;

import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/29 18:24
 **/
@FeignClient(value = ServiceNameConstants.ACTIVITI_SERVICE)
public interface FormPermissionServiceApi {
    @PostMapping("/formPermission/save")
    Result<Void> addPermission(@RequestBody FormPermission formPermission);

}
