package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiResourcesService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.ResourcesServiceApi;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:54
 **/
@Service
public class ApiFeignResourcesServiceImpl implements ApiResourcesService {

    @Autowired
    private ResourcesServiceApi resourcesServiceApi;

    @Override
    public List<String> listPermissions(String userId, String appCode) {
        Result<List<String>> result = resourcesServiceApi.listPermissions(userId, appCode);
        return  null!=result && null!=result.getData() ? result.getData() : null;
    }
}
