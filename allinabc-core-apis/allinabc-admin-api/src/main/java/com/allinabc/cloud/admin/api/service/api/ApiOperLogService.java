package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.dto.OperLogDTO;
import com.allinabc.cloud.common.web.pojo.resp.Result;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:51
 **/
public interface ApiOperLogService {

    /**
     * 新增单条操作日志
     */
    Result<Void> insertOperLog(OperLogDTO operLog);
}
