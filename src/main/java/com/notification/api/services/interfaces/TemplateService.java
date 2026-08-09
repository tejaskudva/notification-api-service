package com.notification.api.services.interfaces;

import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.TemplateResponse;

public interface TemplateService {

    TemplateResponse createTemplate(CreateTemplateRequest templateRequest);

    Object filterTemplate(TemplateFilterRequest request);

    TemplateResponse updateTemplate(String id, UpdateTemplateRequest templateRequest);

    void deleteTemplate(String id);

}
