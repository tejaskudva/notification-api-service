package com.notification.api.models.request;

import java.util.Map;

import com.notification.api.constants.ErrorConstants;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommonUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendNotifRequest {

    @NotBlank(message = ErrorConstants.TEMPLATE_ID_REQUIRED)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

    @AssertTrue
    public boolean validateNotifType() {

        if (CommonUtils.isEmpty(notificationType)) {
            throw new ValidationException(ErrorConstants.NOTIF_TYPE_MISSING_VALIDATION_BREACH);
        }

        if (CommonUtils.isNotEmpty(dynamicVariables)) {
            throw new ValidationException(ErrorConstants.NOTIF_VARS_MISSING_VALIDATION_BREACH);
        }

        return true;
    }

}