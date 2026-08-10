package com.notification.api.pubsub.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.notification.api.pubsub.interfaces.RabbitMQProvider;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(value = "messaging.fallback.rabitmq.enabled", havingValue = "true")
public class RabbitMQProviderImpl implements RabbitMQProvider {

    @Override
    public boolean sendNotification(String topic, String message) {
        log.info("Sending Notif using RabbitMQ for Topic: {} and message: {}", topic, message);
        return false;
    }

}