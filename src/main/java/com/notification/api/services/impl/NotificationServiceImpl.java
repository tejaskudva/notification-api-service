package com.notification.api.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.notification.api.constants.ErrorConstants;
import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.InjestTopicDTO;
import com.notification.api.models.request.SendNotifRequest;
import com.notification.api.services.interfaces.NotificationService;
import com.notification.api.utils.CommonUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final TemplateDao templateDao;

    @Override
    public void sendNotification(final SendNotifRequest request) {

        Optional<Template> template = templateDao.findByTenantIdAndId(CommonUtils.getCurrentTenantId(),
                UUID.fromString(request.getTemplateId()));

        if (template.isEmpty()) {
            // TODO send to audit topic;
            
            throw new ValidationException(ErrorConstants.TEMPLATE_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST.value());
        }

        InjestTopicDTO injestTopicDTO = new InjestTopicDTO();
        injestTopicDTO.setRequestId(CommonUtils.getCurrentTraceId());
        injestTopicDTO.setTenantId(CommonUtils.getCurrentTenantId().toString());
        injestTopicDTO.setReceivedAt(CommonUtils.getCurrentTimeStamp());
        injestTopicDTO.setTemplateId(request.getTemplateId());
        injestTopicDTO.setDynamicVariables(request.getDynamicVariables());
        injestTopicDTO.setNotificationType(request.getNotificationType());

        // TODO publish to ingest topic

    }

}
