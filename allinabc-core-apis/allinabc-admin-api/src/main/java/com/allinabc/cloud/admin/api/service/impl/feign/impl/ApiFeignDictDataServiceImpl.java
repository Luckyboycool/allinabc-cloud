package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiDictDataService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.DictDataServiceApi;
import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Feign服务层
 */
@Service
public class ApiFeignDictDataServiceImpl implements ApiDictDataService {

    @Autowired
    private DictDataServiceApi dictDataServiceApi;


    @Override
    public List<DictData> getDictValueByTypeAndCode(String dictType, String appCode) {
        Result<List<DictData>> result = dictDataServiceApi.getDictValueByTypeAndCode(dictType, appCode);
        return  null!=result && null!=result.getData() ? result.getData() : null;
    }
}
