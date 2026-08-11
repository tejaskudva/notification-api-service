package com.notification.api.models.request;

import java.util.Map;

import com.notification.api.constants.ErrorConstants;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommonUtils;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class UpdateTemplateRequest {

    private String name;
    private Map<String, String> templateVariables;
    private String messageTemplate;

    @AssertTrue
    public boolean validateTemplateVariable() {

        if (CommonUtils.isNotEmpty(templateVariables) && templateVariables.size() > 20) {
            throw new ValidationException(ErrorConstants.TEMPLATE_VARS_VALIDATION_BREACH);
        }

        if (CommonUtils.isNotEmpty(name) && name.trim().length() > 100) {
            throw new ValidationException(ErrorConstants.TEMPLATE_NAME_VALIDATION_BREACH);
        }

        if (CommonUtils.isNotEmpty(messageTemplate) && name.trim().length() > 10000) {
            throw new ValidationException(ErrorConstants.TEMPLATE_MSG_VALIDATION_BREACH);
        }

        if (CommonUtils.isEmpty(name) && CommonUtils.isEmpty(messageTemplate)
                && CommonUtils.isEmpty(templateVariables)) {
            throw new ValidationException(ErrorConstants.TEMPLATE_UPDATE_EMPTY_VALIDATION_BREACH);
        }

        return true;
    }
}