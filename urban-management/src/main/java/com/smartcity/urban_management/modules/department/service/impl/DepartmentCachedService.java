package com.smartcity.urban_management.modules.department.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.infrastructure.redis.cache.DepartmentCacheService;
import com.smartcity.urban_management.modules.department.dto.department.*;
import com.smartcity.urban_management.modules.department.service.DepartmentService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class DepartmentCachedService implements DepartmentService {

    private final DepartmentCacheService cache;
    private final CategoryCacheService categoryCacheService;
    private final DepartmentServiceImpl delegate;

    @Override
    public PageResponse<DepartmentSummaryResponse> getAll(
            DepartmentFilterRequest filter,
            PageRequestDto request
    ) {
        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();
        String filterKey = buildFilterKey(filter);

        return cache.getDepartmentPage(page, size, sort, filterKey)
                .orElseGet(() -> {
                    // query DB qua delegate
                    PageResponse<DepartmentSummaryResponse> response =
                            delegate.getAll(filter, request);

                    // cache lại
                    cache.cacheDepartmentPage(page, size, sort, filterKey, response);

                    return response;
                });
    }

    private String buildFilterKey(DepartmentFilterRequest filter) {
        return String.format(
                "keyword:%s|active:%s",
                filter.getKeyword() == null ? "" : filter.getKeyword(),
                filter.getActive() == null ? "" : filter.getActive()
        );
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(String code) {
        return cache.getDepartmentByCode(code)
                .orElseGet(() -> {
                    DepartmentDetailResponse response = delegate.getDepartmentDetail(code);
                    cache.cacheDepartmentByCode(code, response);
                    return response;
                });
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(UUID id) {
        return cache.getDepartmentById(id)
                .orElseGet(() -> {
                    DepartmentDetailResponse response = delegate.getDepartmentDetail(id);
                    cache.cacheDepartmentById(id, response);
                    return response;
                });
    }

    @Transactional
    @Override
    public CreateDepartmentResponse createDepartment(CreateDepartmentRequest request) {
        CreateDepartmentResponse response = delegate.createDepartment(request);

        // Evict cache list / pages
        cache.evictDepartmentList();
        cache.evictAllPages();

        return response;
    }

    @Override
    public List<DepartmentResponse> getAllActiveByCodes(List<String> codes) {
        // Nếu muốn cache list active cũng có thể thêm, hoặc giữ query DB mỗi lần
        return delegate.getAllActiveByCodes(codes);
    }

    @Transactional
    @Override
    public void deleteDepartment(UUID departmentId, boolean force) {
        // 1. Xóa DB qua delegate
        delegate.deleteDepartment(departmentId, force);

        // 2. Evict cache
        categoryCacheService.evictAllPages();
        cache.evictDepartmentById(departmentId);
        cache.evictDepartmentList();
        cache.evictAllPages();
    }

    @Override
    public boolean hasCategories(UUID departmentId) {
        return delegate.hasCategories(departmentId);
    }

    @Transactional
    @Override
    public UpdateDepartmentResponse updateDepartment(UUID id, UpdateDepartmentRequest request) {
        UpdateDepartmentResponse response = delegate.updateDepartment(id, request);

        cache.evictDepartmentById(id);
        cache.evictDepartmentList();
        cache.evictAllPages();

        return response;
    }
}
