package com.allinabc.cloud.starter.form.handler.manager;

import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.starter.form.annotation.BizType;
import com.allinabc.cloud.starter.form.handler.BizFormHandler;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class BizFormHandlerManager {

    private static Map<String, BizFormHandler> formHandlerMap = Maps.newConcurrentMap();
    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        log.info("Biz Form Handler init begin...");
        Map<String, BizFormHandler> typeMap= context.getBeansOfType(BizFormHandler.class);
        typeMap.forEach((key, value) -> formHandlerMap.put(value.getClass().getAnnotation(BizType.class).value(), value));
        log.info("Biz Form Handler init end...");
    }

    public static BizFormHandler get(String bizType){
        log.info("Get biz form handler with type [{}]", bizType);
        if(null == formHandlerMap || null == formHandlerMap.get(bizType)) {
            throw new BusinessException("Business form type is missing with key: " + bizType);
        }
        return formHandlerMap.get(bizType);
    }

}
