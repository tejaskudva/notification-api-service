package com.notification.api.models.request;

import java.lang.reflect.Field;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.utils.CommonUtils;

import lombok.Data;

@Data
public abstract class BaseSearchDTO<T> {

    private Integer page;
    private Integer size;
    private SortRequest sortRequest;

    public PageRequest buildPageRequest() {

        Sort sort = Optional.ofNullable(sortRequest)
                .filter(CommonUtils::isNotEmpty)
                .filter(req -> CommonUtils.isNotEmpty(req.getSortKey()) && CommonUtils.isNotEmpty(req.getSortType()))
                .filter(req -> {
                    try {
                        this.getClass().getDeclaredField(req.getSortKey());
                        return true;

                    } catch (NoSuchFieldException | SecurityException e) {
                        throw new ValidationException("Invalid Sort Key", HttpStatus.BAD_REQUEST.value());
                    }

                }).map(req -> Sort.by(Sort.Direction.fromString(req.getSortType().getValue()), req.getSortKey()))
                .orElse(Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt"));

        System.out.println("Page: " + page);
        System.out.println("Size: " + size);

        return PageRequest.of(Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(10),
                sort);
    }

    public Example<T> buildSearch() {

        try {
            Class<T> clazz = getEntity();
            T instance = clazz.getDeclaredConstructor().newInstance();

            injectTenantId(instance);

            for (Field dtoField : this.getClass().getDeclaredFields()) {

                dtoField.setAccessible(true);
                Object fieldValue = dtoField.get(this);

                if (CommonUtils.isNotEmpty(fieldValue)) {
                    Field entityField = getField(clazz, dtoField.getName());
                    entityField.setAccessible(true);
                    entityField.set(instance, fieldValue);
                }
            }

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withIgnoreCase()
                    .withIgnoreNullValues();

            return Example.of(instance, matcher);

        } catch (Exception e) {
            throw new ValidationException("Error in Search Builder",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    private void injectTenantId(Object instance) {

        if (NotificationContextHolder.getContext().ignoreTenantIdInjection()) {
            System.out.println("Ignoring tenantId Injection");
            return;
        }

        Field tenantIdField = getField(instance.getClass(), "tenantId");
        tenantIdField.setAccessible(true);
        try {
            tenantIdField.set(instance, CommonUtils.getCurrentTenantId());

        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new ValidationException("Error while Setting TenantId in Search Builder",
                    HttpStatus.BAD_REQUEST.value());
        }
    }

    public Field getField(final Class<?> clazz, final String name) {
        Class<?> current = clazz;

        while (current != null) {
            try {
                return current.getDeclaredField(name);

            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }

    public abstract Class<T> getEntity();
}