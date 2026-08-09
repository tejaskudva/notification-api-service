package com.notification.api.filter;

import java.io.IOException;

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
            }

            NotificationContextHolder.setContext(new NotificationContext(tenantId));
        }

        filterChain.doFilter(request, response);

        if (isValidApi(request.getRequestURI())) {
            NotificationContextHolder.clear();
        }
    }

    private boolean isValidApi(String apiPath) {
        System.out.println("API Path: " + apiPath);
        return apiPath.startsWith("/api");
    }
}