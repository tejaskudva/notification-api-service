package com.notification.api.models.response;

import java.util.Map;

import com.notification.api.models.entity.Template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponseDTO {

    private String id;
    private String name;
    private String messageTemplate;
    private Map<String, String> templateVariables;

    public TemplateResponseDTO(Template template) {
        setId(template.getId().toString());
        setName(template.getName());
        setMessageTemplate(template.getMessageTemplate());
        setTemplateVariables(template.getTemplateVariables());
    }
}