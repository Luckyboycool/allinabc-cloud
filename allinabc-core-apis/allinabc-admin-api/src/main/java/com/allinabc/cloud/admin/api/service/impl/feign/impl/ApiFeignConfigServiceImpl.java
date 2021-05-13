package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiConfigService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.ConfigServiceApi;
import com.allinabc.cloud.admin.pojo.po.Config;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:40
 **/
@Service
public class ApiFeignConfigServiceImpl implements ApiConfigService {

    @Autowired
    private ConfigServiceApi configServiceApi;

    @Override
    public Config getByKey(String key) {
        Result<Config> byKey = configServiceApi.getByKey(key);
        return null!=byKey && null!=byKey.getData() ? byKey.getData() : null;
    }

    @Override
    public Config getByKeyAndApp(String appCode, String key) {
        Result<Config> byKeyAndApp = configServiceApi.getByKeyAndApp(appCode, key);
        return  null!=byKeyAndApp && null!=byKeyAndApp.getData() ? byKeyAndApp.getData() : null;
    }
}
