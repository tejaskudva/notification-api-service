package com.notification.api.dao.impl;

import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.api.constants.ApplicationConstants;
import com.notification.api.constants.ErrorConstants;
import com.notification.api.dao.interfaces.CacheService;
import com.notification.api.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class CacheServiceImpl implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper;

    private String getTenantCacheKey(final String tenantId) {
        return ApplicationConstants.TEMPLATE_REDIS_PREFIX.concat(tenantId);
    }

    @Override
    public <T> void putById(final String tenantId, final String id, T value) {
        put(tenantId, "BY_ID:".concat(id), value);
    }

    @Override
    public <T> void putByName(final String tenantId, final String name, T value) {
        put(tenantId, "BY_NAME:".concat(name), value);
    }

    @Override
    public void deleteById(final String tenantId, final String id) {
        delete(tenantId, "BY_ID:".concat(id));
    }

    @Override
    public void deleteByName(final String tenantId, final String name) {
        delete(tenantId, "BY_NAME:".concat(name));
    }

    @Override
    public <T> Optional<T> getById(final String tenantId, final String id, Class<T> value) {
        return get(tenantId, "BY_ID:".concat(id), value);
    }

    @Override
    public <T> Optional<T> getByName(final String tenantId, final String name, Class<T> value) {
        return get(tenantId, "BY_ID:".concat(name), value);
    }

    private <T> void put(final String tenantId, final String hashKey, T data) {
        try {
            String json = mapper.writeValueAsString(data);
            redisTemplate.opsForHash().put(getTenantCacheKey(tenantId), hashKey, json);

        } catch (JsonProcessingException e) {
            throw new ValidationException(ErrorConstants.CACHE_PUT_ERROR, HttpStatus.BAD_REQUEST.value());
        }
    }

    private void delete(final String tenantId, final String hashKey) {
        redisTemplate.opsForHash().delete(getTenantCacheKey(tenantId), hashKey);
    }

    private <T> Optional<T> get(final String tenantId, final String hashKey, Class<T> clazz) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String jsonData = ops.get(getTenantCacheKey(tenantId), hashKey);
        try {
            return Optional.ofNullable(mapper.readValue(jsonData, clazz));
        } catch (Exception e) {
            throw new ValidationException(ErrorConstants.CACHE_GET_ERROR, HttpStatus.BAD_REQUEST.value());
        }
    }
}