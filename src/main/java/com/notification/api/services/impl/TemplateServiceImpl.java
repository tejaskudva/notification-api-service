package com.notification.api.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.CreateUpdateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.models.response.TemplateResponseDTO;
import com.notification.api.services.interfaces.TemplateService;
import com.notification.api.utils.CommonUtils;
import com.notification.api.constants.ErrorConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateServiceImpl implements TemplateService {

    private final TemplateDao templateDao;

    @Override
    public TemplateResponse createTemplate(CreateUpdateTemplateRequest templateRequest) {

        NotificationContext context = NotificationContextHolder.getContext();

        templateDao.findByTenantIdAndName(context.tenantId(), templateRequest.getName()).ifPresent(template -> {
            throw new ValidationException(ErrorConstants.TEMPLATE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST.value());
        });

        Template template = new Template();
        template.setId(CommonUtils.generateUUID());
        template.setTenantId(UUID.fromString(context.tenantId()));
        BeanUtils.copyProperties(templateRequest, template);
        template.entityCreated();

        templateDao.save(template);

        return new TemplateResponse(template);
    }

    @Override
    public Object filterTemplate(TemplateFilterRequest request) {

        NotificationContextHolder.ignoreTenantIdInjection();

        Page<Template> templates = templateDao.filterTemplate(request.buildSearch(), request.buildPageRequest());
        List<TemplateResponseDTO> data = templates.getContent().stream().map(TemplateResponseDTO::new).toList();
        return new FilterTemplateResponse(data, templates.hasNext(), templates.getTotalElements());
    }

    @Override
    public TemplateResponse updateTemplate(String id, CreateUpdateTemplateRequest templateRequest) {


        return null;
    }

}