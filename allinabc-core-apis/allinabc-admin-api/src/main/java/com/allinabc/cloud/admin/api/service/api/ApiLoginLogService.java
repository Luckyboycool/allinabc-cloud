package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.common.web.pojo.resp.Result;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:49
 **/
public interface ApiLoginLogService {

    /**
     * 新增单条登录日志api
     */
    Result<Void> insertLoginLog(LoginInfoDTO loginInfo);
}
