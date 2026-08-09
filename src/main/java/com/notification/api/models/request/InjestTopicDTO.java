package com.notification.api.models.request;

import java.util.Map;

import lombok.Data;

@Data
public class InjestTopicDTO {

    private String requestId;
    private String tenantId;
    private Long receivedAt;

    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

}
