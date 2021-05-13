package com.allinabc.cloud.activiti.config;

import com.allinabc.cloud.activiti.adapt.DecisionAdapt;
import com.allinabc.cloud.activiti.adapt.NodeAuditorAdapt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/7 18:33
 **/
@Component
@Slf4j
public class ExecuteManager {

    private final Map<String, DecisionAdapt> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public void stragegyInteface(Map<String, DecisionAdapt> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
        log.info("decision初始化结束");
    }


    public DecisionAdapt getDecisionService(String decisionServiceBeanName){
        if(StringUtils.isEmpty(decisionServiceBeanName)){
            log.error("未找到该节点配置的实现类");
            return null;
        }else{
            return this.strategyMap.get(decisionServiceBeanName);
        }

    }
}
