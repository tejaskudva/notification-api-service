package com.notification.api.pubsub.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.notification.api.pubsub.interfaces.AwsSqsProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConditionalOnProperty(value = "messaging.providers.aws.enabled", havingValue = "true")
class AwsSqsProviderImpl implements AwsSqsProvider {

    @Override
    public boolean sendNotification(String topic, String message) {
        
        log.info("AWS SQS data published to topic: {}", topic);

        return false;
    }

}