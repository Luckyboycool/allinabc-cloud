package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.po.DictData;

import java.util.List;


public interface ApiDictDataService {

    /**
     * 根据dictType和appCode获取DictData列表
     *
     * @param dictType
     * @param appCode
     * @return
     */
    List<DictData> getDictValueByTypeAndCode(String dictType, String appCode);


}
