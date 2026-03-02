package com.smartcity.urban_management.shared.pagination;

import org.springframework.data.domain.*;

public final class PageableFactory {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    // chống DDOS query
    private static final int MAX_SIZE = 50;

    private PageableFactory() {}

    public static Pageable from(PageRequestDto request, SortResolver resolver) {

        int page = request.getPage() == null
                ? DEFAULT_PAGE
                : Math.max(request.getPage() - 1, 0);

        int size = request.getSize() == null
                ? DEFAULT_SIZE
                : Math.min(Math.max(request.getSize(), 1), MAX_SIZE);

        Sort sort = parseSort(request.getSort(), resolver);

        return PageRequest.of(page, size, sort);
    }

    private static Sort parseSort(
            String sort,
            SortResolver resolver
    ) {

        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, resolver.defaultField());
        }

        String[] parts = sort.split(",");

        String field = resolver.resolveField(parts[0]);

        Sort.Direction direction =
                parts.length > 1 && parts[1].equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        return Sort.by(direction, field);
    }
}