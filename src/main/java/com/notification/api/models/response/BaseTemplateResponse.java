package com.notification.api.models.response;

import java.util.List;

import lombok.Data;

@Data
public class BaseTemplateResponse <I, R extends Number> {
    
    private List<I> data;
    private boolean hasMoreElements;
    private R totalCount;
}
