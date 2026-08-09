package com.notification.api.utils;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.ObjectUtils;

import com.notification.api.models.context.NotificationContextHolder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

    private static final Calendar cal = Calendar.getInstance();

    public static long getCurrentTimeStamp() {
        return cal.getTimeInMillis();
    }

    public static boolean isNotEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static boolean isEmpty(final Object input) {
        return ObjectUtils.isEmpty(input);
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static UUID getCurrentTenantId() {
        return UUID.fromString(NotificationContextHolder.getContext().tenantId());
    }

}