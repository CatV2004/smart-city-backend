package com.smartcity.urban_management.modules.department.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.infrastructure.redis.cache.DepartmentCacheService;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.department.dto.department.*;
import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.department.mapper.DepartmentMapper;
import com.smartcity.urban_management.modules.department.pagination.DepartmentSortField;
import com.smartcity.urban_management.modules.department.repository.DepartmentRepository;
import com.smartcity.urban_management.modules.department.service.DepartmentService;
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
        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, departmentSortField);

        // ===== CLEAN KEYWORD =====
        String keyword = filter.getKeyword();
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        Boolean active = filter.getActive();

        // ===== QUERY =====
        Page<DepartmentSummaryResponse> pageResult =
                departmentRepository.search(keyword, active, pageable);

        // ===== MAP TO RESPONSE =====
        return PageMapper.toResponse(pageResult);
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(String code) {
        Department department = departmentRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        return mapper.toDetail(department);
    }

    @Override
    public DepartmentDetailResponse getDepartmentDetail(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        return mapper.toDetail(department);
    }

    @Transactional
    @Override
    public CreateDepartmentResponse createDepartment(CreateDepartmentRequest request) {
        if (departmentRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.DEPARTMENT_CODE_ALREADY_EXISTS);
        }

        Department department = mapper.toEntity(request);
        Department saved = departmentRepository.save(department);

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
        }

        departmentRepository.delete(department);
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

        return new UpdateDepartmentResponse(updated.getId());
    }
}
