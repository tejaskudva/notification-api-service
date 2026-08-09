package com.notification.api.models.request;

import lombok.Getter;

@Getter
public enum NotificationType {
    EMAIL("EMAIL"),
    SMS("SMS"),
    WEBHOOK("WEBHOOK");

    private final String value;

    NotificationType(final String value) {
        this.value = value;
    }
}