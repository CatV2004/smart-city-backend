package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.OfficeCacheKeys;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfficeCacheService {

    private final RedisCacheService cacheService;

    private static final Duration PAGE_TTL = Duration.ofMinutes(10);
    private static final Duration DETAIL_TTL = Duration.ofMinutes(30);

    /* =========================================================
       OFFICE DETAIL
       ========================================================= */
    public Optional<DepartmentOfficeResponse> getOffice(UUID id) {
        return cacheService.get(OfficeCacheKeys.officeDetail(id), DepartmentOfficeResponse.class);
    }

    public void cacheOffice(UUID id, DepartmentOfficeResponse data) {
        cacheService.set(OfficeCacheKeys.officeDetail(id), data, DETAIL_TTL);
    }

    public void evictOffice(UUID id) {
        cacheService.delete(OfficeCacheKeys.officeDetail(id));
    }

    /* =========================================================
       OFFICE PAGE
       ========================================================= */
    public Optional<PageResponse<DepartmentOfficeResponse>> getOfficePage(
            UUID departmentId, int page, int size, String sort) {

        return cacheService.get(
                OfficeCacheKeys.officePage(departmentId, page, size, sort),
                (Class) PageResponse.class
        );
    }

    public void cacheOfficePage(
            UUID departmentId, int page, int size, String sort,
            PageResponse<DepartmentOfficeResponse> data) {

        cacheService.set(
                OfficeCacheKeys.officePage(departmentId, page, size, sort),
                data,
                PAGE_TTL
        );
    }

    public void evictAllPages() {
        cacheService.deleteByPattern(OfficeCacheKeys.officePagePattern());
    }
}