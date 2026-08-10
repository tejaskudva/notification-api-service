package com.notification.api.pubsub.publisher;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.api.config.ApplicationProperties;
import com.notification.api.exception.ValidationException;
import com.notification.api.pubsub.fallback.GenericFallbackProvider;
import com.notification.api.pubsub.primary.GenericProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class GenericPublisherImpl implements GenericPublisher {

    private final List<GenericProvider> genericProvider;
    private final List<GenericFallbackProvider> genericFallbackProvider;

    private final ObjectMapper mapper;
    private final ApplicationProperties props;

    @Override
    public void sendDataToIngest(final Object input) {
        sendNotification(props.getIngestTopic(), convertDataIntoString(input));
    }

    @Override
    public void sendDataToAudit(final Object input) {
        sendNotification(props.getAuditTopic(), convertDataIntoString(input));
    }

    @Override
    public void sendNotification(final String topic, final String message) {

        log.info("Sending notification using generic publisher");

        AtomicBoolean success = new AtomicBoolean(false);

        genericProvider.forEach(publisher -> {

            boolean publisherStatus = publisher.sendNotification(topic, message);

            if (!success.get()) {
                success.set(publisherStatus);
            }

            if (publisherStatus) {
                log.info("Notification sent to topic: {} using provider: {}", topic,
                        publisher.getClass().getSimpleName());
            } else {
                log.error("Error while publishing data to topic: {} using provider: {}", topic,
                        publisher.getClass().getSimpleName());
            }
        });

        log.info("Sending notification using fallback publisher");

        genericFallbackProvider.forEach(fallback -> {

            if (success.get()) {
                return;
            }

            boolean fallbackPublisherStatus = fallback.sendNotification(topic, message);

            if (fallbackPublisherStatus) {
                success.set(true);
                log.info("Notification sent to topic: {} using provider: {}", topic,
                        fallback.getClass().getSimpleName());
            } else {
                log.error("Error while publishing data to topic: {} using provider: {}", topic,
                        fallback.getClass().getSimpleName());
            }
        });
    }

    public String convertDataIntoString(Object input) {
        try {
            return mapper.writeValueAsString(input);

        } catch (JsonProcessingException e) {
            log.error("Error while parsing input payload: ", e);
            throw new ValidationException("Error while parsing input payload",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}