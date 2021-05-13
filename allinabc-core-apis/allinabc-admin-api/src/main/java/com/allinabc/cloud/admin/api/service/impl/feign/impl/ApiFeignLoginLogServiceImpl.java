package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiLoginLogService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.LoginLogServiceApi;
import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:50
 **/
@Service
public class ApiFeignLoginLogServiceImpl implements ApiLoginLogService {

    @Autowired
    private LoginLogServiceApi loginLogServiceApi;

    @Override
    public Result<Void> insertLoginLog(LoginInfoDTO loginInfo) {
        return loginLogServiceApi.insertLoginLog(loginInfo);
    }
}
