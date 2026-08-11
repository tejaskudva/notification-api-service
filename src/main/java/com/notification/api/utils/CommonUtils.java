package com.notification.api.utils;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

import com.notification.api.constants.ApplicationConstants;
import com.notification.api.models.context.NotificationContextHolder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

    private static final Calendar cal = Calendar.getInstance();
    private static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{([A-Za-z0-9_]+)}");

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

    public static String getCurrentTraceId() {
        return MDC.get(ApplicationConstants.X_REQUEST_ID);
    }

    public static Set<String> extractDynamicVars(final String template) {

        Set<String> vars = new HashSet<>();
        Matcher matcher = TEMPLATE_PATTERN.matcher(template);

        while (matcher.find()) {
            vars.add(matcher.group(1));
        }

        return vars;
    }
}