package com.smartcity.urban_management.modules.department.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.MapCacheService;
import com.smartcity.urban_management.infrastructure.redis.cache.OfficeCacheService;
import com.smartcity.urban_management.modules.department.dto.office.*;
import com.smartcity.urban_management.modules.department.service.DepartmentOfficeService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class OfficeCacheServiceImpl implements DepartmentOfficeService {

    private final OfficeCacheService cacheService;
    private final MapCacheService mapCacheService;

    private final DepartmentOfficeService delegate;

    @Override
    public CreateOfficeResponse create(DepartmentOfficeRequest request) {

        CreateOfficeResponse res = delegate.create(request);

        cacheService.evictAllPages();
        mapCacheService.evictAllMapData();

        return res;
    }

    @Override
    public PageResponse<DepartmentOfficeResponse> getByDepartment(
            UUID departmentId, PageRequestDto request) {

        var cached = cacheService.getOfficePage(
                departmentId,
                request.getPage(),
                request.getSize(),
                request.getSort()
        );

        if (cached.isPresent()) {
            log.info("🔥 Office page cache HIT");
            return cached.get();
        }

        log.info("❌ Office page cache MISS");

        var data = delegate.getByDepartment(departmentId, request);

        cacheService.cacheOfficePage(
                departmentId,
                request.getPage(),
                request.getSize(),
                request.getSort(),
                data
        );

        return data;
    }

    @Override
    public void delete(UUID id) {

        delegate.delete(id);

        cacheService.evictAllPages();
        cacheService.evictOffice(id);
        mapCacheService.evictAllMapData();
    }

    @Override
    public void addUserToOffice(UUID officeId, AddUserToOfficeRequest request) {

        delegate.addUserToOffice(officeId, request);
    }
}