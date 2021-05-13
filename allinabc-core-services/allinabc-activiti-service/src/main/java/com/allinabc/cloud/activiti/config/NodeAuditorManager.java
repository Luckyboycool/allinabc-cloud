package com.allinabc.cloud.activiti.config;

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
 * @Date 2021/4/7 17:34
 **/
@Component
@Slf4j
public class NodeAuditorManager {

    private final Map<String, NodeAuditorAdapt> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public void stragegyInteface(Map<String, NodeAuditorAdapt> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
        log.info("节点审批初始化结束");
    }


    public NodeAuditorAdapt getNodeAuditorService(String nodeServiceBeanName){
        if(StringUtils.isEmpty(nodeServiceBeanName)){
            log.error("未找到该节点配置的实现类");
            return null;
        }else{
            return this.strategyMap.get(nodeServiceBeanName);
        }

    }
}
