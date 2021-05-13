package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign服务层
 */
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface DictDataServiceApi {

    /**
     * 根据dictType和appCode获取DictData列表
     *
     * @param dictType
     * @param appCode
     * @return
     */
    @GetMapping("/dict/data/get-list")
    Result<List<DictData>> getDictValueByTypeAndCode(@RequestParam(value = "dictType") String dictType, @RequestParam(value = "appCode") String appCode);


}
