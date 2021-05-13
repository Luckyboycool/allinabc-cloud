package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface ResourcesServiceApi {

    @GetMapping("/resources/list/perms/{userId}/{appCode}")
    Result<List<String>> listPermissions(@PathVariable("userId") String userId, @PathVariable("appCode") String appCode);

}
