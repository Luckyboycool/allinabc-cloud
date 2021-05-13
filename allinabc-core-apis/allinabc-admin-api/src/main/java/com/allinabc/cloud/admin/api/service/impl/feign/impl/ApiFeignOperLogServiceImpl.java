package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiOperLogService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.OperLogServiceApi;
import com.allinabc.cloud.admin.pojo.dto.OperLogDTO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:52
 **/
@Service
public class ApiFeignOperLogServiceImpl implements ApiOperLogService {

    @Autowired
    private OperLogServiceApi operLogServiceApi;

    @Override
    public Result<Void> insertOperLog(OperLogDTO operLog) {
         return operLogServiceApi.insertOperLog(operLog);
    }
}
