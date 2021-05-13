package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.po.Config;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:38
 **/
public interface ApiConfigService {

    Config getByKey(String key);


    Config getByKeyAndApp(String appCode, String key);
}
