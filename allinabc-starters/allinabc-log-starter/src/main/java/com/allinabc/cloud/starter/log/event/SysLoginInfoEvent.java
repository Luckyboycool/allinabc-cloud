package com.allinabc.cloud.starter.log.event;

import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 */
public class SysLoginInfoEvent extends ApplicationEvent {

    public SysLoginInfoEvent(LoginInfoDTO source) {
        super(source);
    }

}