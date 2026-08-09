package com.notification.api.dao.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.dao.repository.TemplateRepository;
import com.notification.api.models.entity.Template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;

    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {
        return templateRepository.findByNameIgnoreCaseAndTenantId(templateName, UUID.fromString(tenantId));
    }

    @Override
    public Template save(final Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> search,
            final PageRequest pageRequest) {

        return templateRepository.findAll(search, pageRequest);
    }

    @Override
    public Optional<Template> findByTenantIdAndId(final String tenantId, final String id) {
        return templateRepository.findByTenantIdAndId(UUID.fromString(tenantId), UUID.fromString(id));
    }

}
