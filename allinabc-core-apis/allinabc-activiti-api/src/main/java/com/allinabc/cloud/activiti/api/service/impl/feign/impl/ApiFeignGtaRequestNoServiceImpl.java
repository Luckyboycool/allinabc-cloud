package com.allinabc.cloud.activiti.api.service.impl.feign.impl;

import com.allinabc.cloud.activiti.api.service.api.ApiGtaRequestNoService;
import com.allinabc.cloud.activiti.api.service.impl.feign.client.GtaRequestNoServiceApi;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/5 18:24
 **/
@Service
public class ApiFeignGtaRequestNoServiceImpl implements ApiGtaRequestNoService {
    @Autowired
    private GtaRequestNoServiceApi gtaRequestNoServiceApi;
    @Override
    public String getRequestNo(String pre, Integer length) {
        Result<String> requestNo = gtaRequestNoServiceApi.getRequestNo(pre, length);
        if(requestNo.getCode()!=20000){
            throw new BusinessException("调用服务失败");
        }
        return null!=requestNo&& null!=requestNo.getMessage() ? requestNo.getMessage():null;
    }
}
