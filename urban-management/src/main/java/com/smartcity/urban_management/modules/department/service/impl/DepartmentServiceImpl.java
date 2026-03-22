package com.smartcity.urban_management.modules.department.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.infrastructure.redis.cache.DepartmentCacheService;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.department.dto.*;
import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.department.mapper.DepartmentMapper;
import com.smartcity.urban_management.modules.department.pagination.DepartmentSortField;
import com.smartcity.urban_management.modules.department.repository.DepartmentRepository;
import com.smartcity.urban_management.modules.department.service.DepartmentService;
import com.smartcity.urban_management.modules.report.dto.CreateReportResponse;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;
    private final DepartmentSortField departmentSortField = new DepartmentSortField();
    private final DepartmentCacheService departmentCacheService;
    private final CategoryCacheService categoryCacheService;
    private final DepartmentMapper mapper;

    @Override
    public PageResponse<DepartmentSummaryResponse> getAll(
            DepartmentFilterRequest filter,
            PageRequestDto request
    ) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        String filterKey = buildFilterKey(filter);

        // ===== CACHE CHECK =====
        Optional<PageResponse<DepartmentSummaryResponse>> cached =
                departmentCacheService.getDepartmentPage(page, size, sort, filterKey);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, departmentSortField);

        // ===== KEYWORD CLEAN =====
        String keyword = filter.getKeyword();
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) {
                keyword = null;
            }
        }

        Boolean active = filter.getActive();

        // ===== QUERY =====
        Page<DepartmentSummaryResponse> pageResult =
                departmentRepository.search(keyword, active, pageable);

        PageResponse<DepartmentSummaryResponse> response =
                PageMapper.toResponse(pageResult);

        // ===== CACHE SET =====
        departmentCacheService.cacheDepartmentPage(
                page,
                size,
                sort,
                filterKey,
                response
        );

        return response;
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(String code) {

        return departmentCacheService.getDepartmentByCode(code)
                .orElseGet(() -> {

                    Department department = departmentRepository.findByCode(code)
                            .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

                    DepartmentDetailResponse response = mapper.toDetail(department);

                    departmentCacheService.cacheDepartmentByCode(code, response);

                    return response;
                });
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(UUID id) {

        return departmentCacheService.getDepartmentById(id)
                .orElseGet(() -> {

                    Department department = departmentRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

                    DepartmentDetailResponse response = mapper.toDetail(department);

                    departmentCacheService.cacheDepartmentById(id, response);

                    return response;
                });
    }

    @Transactional
    @Override
    public CreateDepartmentResponse createDepartment(CreateDepartmentRequest request) {

        // 1. Validate unique code
        if (departmentRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.DEPARTMENT_CODE_ALREADY_EXISTS);
        }

        // 2. Map to entity
        Department department = mapper.toEntity(request);

        // 3. Save DB
        Department saved = departmentRepository.save(department);

        // 4. Evict cache (list/page)
        departmentCacheService.evictDepartmentList();
        departmentCacheService.evictAllPages();

        return new CreateDepartmentResponse(saved.getId());
    }

    @Override
    public List<DepartmentResponse> getAllActiveByCodes(List<String> codes) {

        List<Department> departments;

        if (codes == null || codes.isEmpty()) {
            departments = departmentRepository.findByIsActiveTrue();
        } else {
            departments = departmentRepository.findByIsActiveTrueAndCodeIn(codes);
        }

        return departments.stream()
                .map(dep -> DepartmentResponse.builder()
                        .id(dep.getId())
                        .name(dep.getName())
                        .code(dep.getCode())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public void deleteDepartment(UUID departmentId, boolean force) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        boolean hasCategories = categoryRepository.existsByDepartmentId(departmentId);

        if (hasCategories && !force) {
            throw new AppException(ErrorCode.DEPARTMENT_HAS_CATEGORIES);
        }

        if (hasCategories) {
            List<String> slugs = categoryRepository.findSlugsByDepartmentId(departmentId);

            categoryRepository.deleteByDepartmentId(departmentId);

            slugs.forEach(categoryCacheService::evictCategoryBySlug);

            categoryCacheService.evictAllPages();
        }

        departmentRepository.delete(department);

        departmentCacheService.evictDepartmentById(departmentId);
        departmentCacheService.evictDepartmentList();
        departmentCacheService.evictAllPages();
    }

    @Override
    public boolean hasCategories(UUID departmentId) {
        return categoryRepository.existsByDepartmentId(departmentId);
    }

    @Transactional
    @Override
    public UpdateDepartmentResponse updateDepartment(UUID id, UpdateDepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        if (request.getName() != null) {
            if (departmentRepository.existsByNameAndIdNot(request.getName(), id)) {
                throw new AppException(ErrorCode.DEPARTMENT_NAME_ALREADY_EXISTS);
            }
            department.setName(request.getName());
        }
        if (request.getDescription() != null) {
            department.setDescription(request.getDescription());
        }
        if (request.getIsActive() != null) {
            department.setIsActive(request.getIsActive());
        }

        Department updated = departmentRepository.save(department);

        departmentCacheService.evictDepartmentById(id);
        departmentCacheService.evictDepartmentList();
        departmentCacheService.evictAllPages();

        return new UpdateDepartmentResponse(updated.getId());
    }

    private String buildFilterKey(DepartmentFilterRequest filter) {
        return String.format(
                "keyword:%s|active:%s",
                filter.getKeyword() == null ? "" : filter.getKeyword(),
                filter.getActive() == null ? "" : filter.getActive()
        );
    }
}
