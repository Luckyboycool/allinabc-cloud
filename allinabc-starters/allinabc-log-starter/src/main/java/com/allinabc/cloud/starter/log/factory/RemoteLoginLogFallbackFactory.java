package com.allinabc.cloud.starter.log.factory;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.log.feign.RemoteLoginLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteLoginLogFallbackFactory implements FallbackFactory<RemoteLoginLogService>
{
    @Override
    public RemoteLoginLogService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return loginInfo -> Result.failed("error when call api [insertLoginLog] ... ");
    }
}
