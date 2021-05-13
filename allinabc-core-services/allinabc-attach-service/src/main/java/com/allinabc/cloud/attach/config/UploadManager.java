package com.allinabc.cloud.attach.config;

import com.allinabc.cloud.attach.adapter.UploadAdapt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/7 17:34
 **/
@Component
@Slf4j
public class UploadManager {

    private final Map<String, UploadAdapt> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public void stragegyInteface(Map<String, UploadAdapt> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
        log.info("上传类型初始化结束");
    }


    public UploadAdapt getUploadService(String uploadBeanName){
        if(StringUtils.isEmpty(uploadBeanName)){
            log.error("未找到该上传类型的实现类");
            return null;
        }else{
            return this.strategyMap.get(uploadBeanName);
        }

    }
}
