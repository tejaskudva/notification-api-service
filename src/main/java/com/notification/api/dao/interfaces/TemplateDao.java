package com.notification.api.dao.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.notification.api.models.entity.Template;

public interface TemplateDao {

    Optional<Template> findByTenantIdAndName(String tenantId, String templateName);

    Template save(Template template);

    Page<Template> filterTemplate(Example<Template> search, PageRequest pageRequest);

    Optional<Template> findByTenantIdAndId(UUID tenantId, UUID id);

    void deleteTemplateById(UUID id);

}
