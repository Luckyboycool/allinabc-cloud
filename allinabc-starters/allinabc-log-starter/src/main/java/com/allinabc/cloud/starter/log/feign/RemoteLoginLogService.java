package com.allinabc.cloud.starter.log.feign;

import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.common.api.interceptor.FeignInterceptor;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.log.factory.RemoteLoginLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/31 10:45
 **/
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE,fallbackFactory = RemoteLoginLogFallbackFactory.class,configuration = FeignInterceptor.class)
public interface RemoteLoginLogService{
    /**
     * 新增单条登录日志api
     */
    @PostMapping("/loginInfo/add")
    Result<Void> insertLoginLog(@RequestBody LoginInfoDTO loginInfo);
}
