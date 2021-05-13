package com.allinabc.cloud.starter.kafka.config;

import com.allinabc.cloud.starter.kafka.properties.KafkaProperties;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * Kafka Topic动态加载配置 (from nacos config kafka.topics)
 */
@Slf4j
@Import(value = {KafkaSender.class,KafkaProperties.class})
public class KafkaTopicConfiguration implements InitializingBean {

    @Resource
    private KafkaProperties properties;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("KafkaTopicConfiguration setting ...");
        String topics = Joiner.on(",").join(properties.getAllTopics());
        log.debug("Properties ..." + topics);
        System.setProperty("topics", topics);
        log.debug("KafkaTopicConfiguration setting end ...");
    }

}
