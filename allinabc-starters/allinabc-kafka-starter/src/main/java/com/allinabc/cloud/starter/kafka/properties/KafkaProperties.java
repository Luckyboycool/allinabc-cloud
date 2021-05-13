package com.allinabc.cloud.starter.kafka.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {


    /** 模型发生的事件 */
    public static final String TOPIC_MODULE_EVENT           = "module-event";
    /** 模型推送的日志 */
    public static final String TOPIC_MODULE_LOG             = "module-log";
    /** 容器中的模型心跳包 */
    public static final String TOPIC_MODULE_HEARTBEAT       = "module-heartbeat";
    /** 模型训练，更新模型版本 */
    public static final String TOPIC_MODULE_VERCHANGE       = "module-verchange";
    /** 更新manbin 发生kafka */
    private static final String TOPIC_MODULE_MANBINCHANGE   = "module-manbinchange";
    /** 上传s3后 发送kafka */
    private static final String TOPIC_MODULE_UPLOADS3   = "module-manbinuploads3";
    /** kafka 服务心跳监测 **/
    private static final String TOPIC_KAFKA_HEARTBEAT       = "kafka-heartbeat";

    private Map<String, String> topics = Maps.newHashMap();

    public String getTopic(String key){
        return topics.get(key);
    }

    public List<String> getAllTopics() {
        return Lists.newArrayList(topics.values());
    }



    public String getTopicModuleEvent(){
        return getTopic(TOPIC_MODULE_EVENT);
    }

    public String getTopicModuleLog(){
        return getTopic(TOPIC_MODULE_LOG);
    }

    public String getTopicModuleHeartbeat(){
        return getTopic(TOPIC_MODULE_HEARTBEAT);
    }

    public String getTopicModuleVerchange(){
        return getTopic(TOPIC_MODULE_VERCHANGE);
    }

    public String getTopicModuleManBinChange(){
        return getTopic(TOPIC_MODULE_MANBINCHANGE);
    }

    public String getTopicKafkaHeartbeat(){
        return getTopic(TOPIC_KAFKA_HEARTBEAT);
    }
    public String getTopicModuleUploads3(){
        return getTopic(TOPIC_MODULE_UPLOADS3);
    }
}
