package com.allinabc.cloud.starter.log.listener;


import com.allinabc.cloud.admin.api.service.api.ApiLoginLogService;
import com.allinabc.cloud.admin.api.service.api.ApiOperLogService;
import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.admin.pojo.dto.OperLogDTO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.starter.log.event.SysLoginInfoEvent;
import com.allinabc.cloud.starter.log.event.SysOperLogEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;

/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class LogListener {

    @Autowired
    private ApiOperLogService remoteOperLogService;

    @Resource
    private ApiLoginLogService remoteLoginLogService;

    @Async
    @Order
    @EventListener(SysOperLogEvent.class)
    public void listenOperLog(SysOperLogEvent event) {
        OperLogDTO sysOperLog = (OperLogDTO) event.getSource();
        Result result = null;
        try {
            result = remoteOperLogService.insertOperLog(sysOperLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if(null == result || !(ApiResultCode.SUCCESS.getCode()==result.getCode())) {
            log.info("远程操作日志记录失败：{}", null != result ? result.getMessage() : "");
        }else {
            log.info("远程操作日志记录成功");
        }

    }

    @Async
    @Order
    @EventListener(SysLoginInfoEvent.class)
    public void listenLoginInfo(SysLoginInfoEvent event) {
        LoginInfoDTO sysLoginInfo = (LoginInfoDTO) event.getSource();
        Result result = remoteLoginLogService.insertLoginLog(sysLoginInfo);
        if(null == result || !(ApiResultCode.SUCCESS.getCode()==result.getCode())) {
            log.info("远程访问日志记录失败：{}", null != result ? result.getMessage() : "");
        }else {
            log.info("远程访问日志记录成功");
        }
    }

}