package com.allinabc.cloud.starter.log.factory;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.log.feign.RemoteOperLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteOperLogFallbackFactory implements FallbackFactory<RemoteOperLogService>
{
    @Override
    public RemoteOperLogService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return operLog -> Result.failed("error when call api [insertOperLog] ... ");
    }
}
