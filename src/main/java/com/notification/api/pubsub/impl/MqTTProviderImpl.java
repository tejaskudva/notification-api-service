package com.notification.api.pubsub.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.notification.api.pubsub.interfaces.MqTTProvider;

import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnProperty(value = "messaging.fallback.mqtt.enabled", havingValue = "true")
@Slf4j
class MqTTProviderImpl implements MqTTProvider {

    @Override
    public boolean sendNotification(String topic, String message) {

        log.info("MQ TT data published to topic: {}", topic);

        return false;
    }

}