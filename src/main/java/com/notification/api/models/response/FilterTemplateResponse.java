package com.notification.api.models.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilterTemplateResponse extends BaseTemplateResponse<TemplateResponseDTO, Long> {

    public FilterTemplateResponse(final List<TemplateResponseDTO> list, final boolean hasMoreElements,
            final Long totalCount) {

        setData(list);
        setHasMoreElements(hasMoreElements);
        setTotalCount(totalCount);
    }

}