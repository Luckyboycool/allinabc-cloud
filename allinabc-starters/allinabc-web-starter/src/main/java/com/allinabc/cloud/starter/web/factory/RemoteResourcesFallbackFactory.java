package com.allinabc.cloud.starter.web.factory;//package com.allinabc.cloud.common.auth.factory;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.web.feign.RemoteResourcesService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/4 13:37
 **/

@Slf4j
public class RemoteResourcesFallbackFactory implements FallbackFactory<RemoteResourcesService> {

    @Override
    public RemoteResourcesService create(Throwable throwable) {
      return (userId, appCode) -> Result.failed("failed");
    }
}
