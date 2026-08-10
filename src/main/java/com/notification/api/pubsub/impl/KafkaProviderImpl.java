package com.notification.api.pubsub.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.notification.api.pubsub.interfaces.KafkaProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "messaging.providers.kafka.enabled", havingValue = "true")
class KafkaProviderImpl implements KafkaProvider {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean sendNotification(String topic, String message) {

        try {
            kafkaTemplate.send(topic, message);
            log.info("Kafka data published to topic: {}", topic);
            return true;

        } catch (Exception e) {
            log.error("Error while publishing data to Kafka for: {} with Error: ", topic, e);
        }

        return false;
    }
}