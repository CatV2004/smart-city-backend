package com.smartcity.urban_management.shared.pagination;

import org.springframework.data.domain.Page;

public final class PageMapper {

    private PageMapper() {}

    public static <T> PageResponse<T> toResponse(Page<T> page) {

        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}