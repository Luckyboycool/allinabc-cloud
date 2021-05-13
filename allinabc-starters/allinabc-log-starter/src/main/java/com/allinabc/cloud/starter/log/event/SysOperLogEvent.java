package com.allinabc.cloud.starter.log.event;

import com.allinabc.cloud.admin.pojo.dto.OperLogDTO;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 */
public class SysOperLogEvent extends ApplicationEvent {

    public SysOperLogEvent(OperLogDTO source) {
        super(source);
    }

}