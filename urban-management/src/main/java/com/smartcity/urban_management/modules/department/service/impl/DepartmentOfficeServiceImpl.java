package com.smartcity.urban_management.modules.department.service.impl;

import com.smartcity.urban_management.modules.department.dto.office.AddUserToOfficeRequest;
import com.smartcity.urban_management.modules.department.dto.office.CreateOfficeResponse;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeRequest;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import com.smartcity.urban_management.modules.department.pagination.DepartmentOfficeSortField;
import com.smartcity.urban_management.modules.department.repository.DepartmentOfficeRepository;
import com.smartcity.urban_management.modules.department.repository.DepartmentRepository;
import com.smartcity.urban_management.modules.department.service.DepartmentOfficeService;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentOfficeServiceImpl implements DepartmentOfficeService {

    private final DepartmentOfficeRepository repository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentOfficeSortField departmentOfficeSortField = new DepartmentOfficeSortField();
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public CreateOfficeResponse create(DepartmentOfficeRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        Point point = geometryFactory.createPoint(
                new Coordinate(request.getLongitude(), request.getLatitude())
        );

        DepartmentOffice entity = DepartmentOffice.builder()
                .department(department)
                .name(request.getName())
                .address(request.getAddress())
                .location(point)
                .isActive(Boolean.TRUE)
                .build();

        DepartmentOffice saved = repository.save(entity);

        return new CreateOfficeResponse(saved.getId());
    }

    @Override
    public PageResponse<DepartmentOfficeResponse> getByDepartment(UUID departmentId, PageRequestDto request) {

        Pageable pageable = PageableFactory.from(request, departmentOfficeSortField);

        Page<DepartmentOfficeResponse> pageResult =
                repository.findOfficesWithUserCount(departmentId, pageable);

        return PageMapper.toResponse(pageResult);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void addUserToOffice(UUID officeId, AddUserToOfficeRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        DepartmentOffice office = repository.findById(officeId)
                .orElseThrow(() -> new AppException(ErrorCode.OFFICE_NOT_FOUND));

        if (user.getOffice() != null &&
                user.getOffice().getId().equals(officeId)) {
            throw new AppException(ErrorCode.USER_ALREADY_IN_OFFICE);
        }

        user.setOffice(office);

        userRepository.save(user);
    }
}
