package com.notification.api.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.notification.api.constants.ApplicationConstants;
import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.utils.CommonUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class NotificationAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isValidApi(request.getRequestURI())) {

            String tenantId = request.getHeader(ApplicationConstants.X_TENANT_ID);

            if (CommonUtils.isEmpty(tenantId)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized! API Key is required.");
                return;
            }

            String requestId = CommonUtils.generateUUID().toString();
            MDC.put(ApplicationConstants.X_REQUEST_ID, requestId);
            response.setHeader(ApplicationConstants.X_REQUEST_ID, requestId);

            NotificationContextHolder.setContext(new NotificationContext(tenantId, false));
        }

        filterChain.doFilter(request, response);

        if (isValidApi(request.getRequestURI())) {
            NotificationContextHolder.clear();
            MDC.clear();
        }
    }

    private boolean isValidApi(String apiPath) {
        return apiPath.startsWith("/api");
    }
}