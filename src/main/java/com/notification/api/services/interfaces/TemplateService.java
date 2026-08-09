package com.notification.api.services.interfaces;

import com.notification.api.models.request.CreateUpdateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.TemplateResponse;

public interface TemplateService {

    TemplateResponse createTemplate(CreateUpdateTemplateRequest templateRequest);

    Object filterTemplate(TemplateFilterRequest request);

    TemplateResponse updateTemplate(String id, CreateUpdateTemplateRequest templateRequest);

}
