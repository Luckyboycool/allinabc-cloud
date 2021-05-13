package com.allinabc.cloud.starter.web.feign;


import com.allinabc.cloud.common.api.interceptor.FeignInterceptor;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.web.factory.RemoteResourcesFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/4 13:35
 **/
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE, path = "/resources", fallbackFactory = RemoteResourcesFallbackFactory.class, configuration = FeignInterceptor.class)
public interface RemoteResourcesService{

    @GetMapping("/list/perms/{userId}/{appCode}")
    Result<List<String>> listPermissions(@PathVariable("userId") String userId, @PathVariable("appCode") String appCode);
}
