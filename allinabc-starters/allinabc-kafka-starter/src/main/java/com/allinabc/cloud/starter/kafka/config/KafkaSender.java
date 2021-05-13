package com.allinabc.cloud.starter.kafka.config;

import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;

public class KafkaSender {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }

}
