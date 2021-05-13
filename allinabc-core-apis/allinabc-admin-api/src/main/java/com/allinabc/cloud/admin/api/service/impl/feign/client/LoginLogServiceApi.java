package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description 登录日志Api
 * @Author wangtaifeng
 * @Date 2020/12/31 10:48
 **/
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface LoginLogServiceApi {

    /**
     * 新增单条登录日志api
     */
    @PostMapping("/loginInfo/add")
    Result<Void> insertLoginLog(@RequestBody LoginInfoDTO loginInfo);
}
