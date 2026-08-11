package com.notification.api.models.request;

import java.util.Map;

import com.notification.api.constants.ErrorConstants;
import com.notification.api.utils.CommonUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTemplateRequest {

    @Size(max = 100, message = ErrorConstants.TEMPLATE_NAME_VALIDATION_BREACH)
    @NotBlank(message = "Name field is required")
    private String name;

    private Map<String, String> templateVariables;

    @Size(max = 10000, message = ErrorConstants.TEMPLATE_MSG_VALIDATION_BREACH)
    @NotBlank(message = "Message template field is required")
    private String messageTemplate;

    @AssertTrue(message = ErrorConstants.TEMPLATE_VARS_VALIDATION_BREACH)
    public boolean validateTemplateVariable() {
        return CommonUtils.isNotEmpty(templateVariables) && templateVariables.size() <= 20;
    }
}