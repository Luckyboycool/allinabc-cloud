package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.po.Config;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Config Feign服务层
 * @author jiangni
 */
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface ConfigServiceApi {

    @GetMapping("/config/get/key/{key}")
    Result<Config> getByKey(@PathVariable(value = "key") String key);

    @GetMapping("/config/get/key/{appCode}/{key}")
    Result<Config> getByKeyAndApp(@PathVariable(value = "appCode") String appCode, @PathVariable(value = "key") String key);

}
