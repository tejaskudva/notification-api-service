package com.notification.api.dao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.notification.api.models.entity.Template;

public interface TemplateRepository extends MongoRepository<Template, UUID> {

    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId);

    Optional<Template> findByTenantIdAndId(UUID tenantId, UUID id);

}
