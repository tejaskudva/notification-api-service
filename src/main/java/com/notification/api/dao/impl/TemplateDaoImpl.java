package com.notification.api.dao.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.notification.api.dao.interfaces.CacheService;
import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.dao.repository.TemplateRepository;
import com.notification.api.models.entity.Template;
import com.notification.api.utils.CommonUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;
    private final CacheService cacheService;

    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {

        return cacheService.getByName(tenantId, templateName, Template.class)
                .or(() -> templateRepository.findByNameIgnoreCaseAndTenantId(templateName, UUID.fromString(tenantId))
                        .map(template -> {
                            cacheService.putByName(tenantId, templateName, Template.class);
                            return template;
                        }));
    }

    @Override
    public Template save(final Template template) {

        cacheService.putById(template.getTenantId().toString(), template.getId().toString(), Template.class);
        cacheService.putByName(template.getTenantId().toString(), template.getName(), Template.class);

        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> search,
            final PageRequest pageRequest) {

        return templateRepository.findAll(search, pageRequest);
    }

    @Override
    public Optional<Template> findByTenantIdAndId(final UUID tenantId, final UUID id) {

        return cacheService.getById(tenantId.toString(), id.toString(), Template.class)
                .or(() -> templateRepository.findByTenantIdAndId(id, tenantId)
                        .map(template -> {
                            cacheService.putById(tenantId.toString(), id.toString(), Template.class);
                            return template;
                        }));
    }

    @Override
    public void deleteTemplateById(final UUID id, final Supplier<? extends Throwable> exceptionHandler) {

        findByTenantIdAndId(CommonUtils.getCurrentTenantId(), id).ifPresentOrElse(template -> {
            cacheService.deleteById(template.getTenantId().toString(), id.toString());
            cacheService.deleteByName(template.getTenantId().toString(), template.getName());
        }, () -> {
            if (CommonUtils.isNotEmpty(exceptionHandler)) {
                exceptionHandler.get();
            }
        });

        templateRepository.deleteById(id);
    }

}