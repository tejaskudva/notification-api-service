package com.notification.api.models.request;

import java.util.Map;

import com.notification.api.constants.ErrorConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendNotifRequest {

    @NotBlank(message = ErrorConstants.TEMPLATE_ID_REQUIRED)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

}