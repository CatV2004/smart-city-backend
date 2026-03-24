package com.smartcity.urban_management.modules.user.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.UserCacheService;
import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.service.UserService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class UserCachedService implements UserService {

    private final UserCacheService userCacheService;
    private final UserServiceImpl delegate;

    @Override
    public UserDetailResponse getCurrentUser(UUID userId) {
        return delegate.getCurrentUser(userId);
    }

    @Transactional
    @Override
    public CreateUserResponse createUserByAdmin(CreateUserRequest request) {
        CreateUserResponse response = delegate.createUserByAdmin(request);

        userCacheService.evictAllUserPages();

        return response;
    }

    @Override
    public PageResponse<UserSummaryResponse> getUsersByDepartment(UUID departmentId, PageRequestDto request) {
        return delegate.getUsersByDepartment(departmentId, request);
    }

    @Override
    public PageResponse<UserSummaryResponse> getUsersByOffice(UUID officeId, PageRequestDto request) {
        return delegate.getUsersByOffice(officeId, request);
    }

    @Override
    public PageResponse<UserSummaryResponse> getAll(UserFilterRequest filter, PageRequestDto request) {
        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        String filterKey = buildFilterKey(filter);

        // ===== CACHE CHECK =====
        Optional<PageResponse<UserSummaryResponse>> cached =
                userCacheService.getUserPage(page, size, sort, filterKey);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CACHE MISS → delegate query =====
        PageResponse<UserSummaryResponse> response = delegate.getAll(filter, request);

        // ===== CACHE SET =====
        userCacheService.cacheUserPage(page, size, sort, filterKey, response);

        return response;
    }

    private String buildFilterKey(UserFilterRequest filter) {

        return String.format(
                "keyword:%s|active:%s|dept:%s|role:%s",
                safe(filter.getKeyword()),
                safe(filter.getActive()),
                safe(filter.getDepartmentId()),
                safe(filter.getRoleId())
        );
    }

    private String safe(Object o) {
        return o == null ? "" : o.toString();
    }
}