package com.smartcity.urban_management.modules.task.service.impl;

import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import com.smartcity.urban_management.modules.department.repository.DepartmentOfficeRepository;
import com.smartcity.urban_management.modules.location.client.RoutingClient;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import com.smartcity.urban_management.modules.task.repository.TaskRepository;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private DepartmentOfficeRepository officeRepository;

    @Mock
    private RoutingClient routingClient;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void autoAssign_shouldAssignToBestOffice_basedOnRoutingDurations() {
        // GIVEN
        UUID reportId = UUID.randomUUID();
        Point location = new GeometryFactory().createPoint(new Coordinate(10.0, 20.0));

        Report report = Report.builder()
                .id(reportId)
                .location(location)
                .build();

        UUID departmentId = UUID.randomUUID();

        // Mock reportRepository
        when(reportRepository.findDepartmentIdByReportId(reportId))
                .thenReturn(departmentId);

        // Mock officeRepository
        DepartmentOffice office1 = DepartmentOffice.builder().id(UUID.randomUUID()).build();
        DepartmentOffice office2 = DepartmentOffice.builder().id(UUID.randomUUID()).build();

        List<DepartmentOffice> candidates = List.of(office1, office2);

        when(officeRepository.findNearestOffices(eq(departmentId), anyDouble(), anyDouble(), anyInt()))
                .thenReturn(candidates);

        // Mock routingClient (durations: office1 = 10, office2 = 5)
        when(routingClient.getDurations(eq(location), eq(candidates)))
                .thenReturn(List.of(0.0, 10.0, 5.0));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        taskService.autoAssign(report);

        // THEN
        // Verify taskRepository.save called with bestOffice = office2 (shortest duration)
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());

        Task savedTask = captor.getValue();
        assertEquals(report, savedTask.getReport());
        assertEquals(TaskStatus.ASSIGNED, savedTask.getStatus());
        assertEquals(office2.getId(), savedTask.getDepartmentOffice().getId());
        assertNotNull(savedTask.getAssignedAt());
    }

    @Test
    void autoAssign_shouldFallbackToNearest_whenRoutingFails() {
        UUID reportId = UUID.randomUUID();
        Point location = new GeometryFactory().createPoint(new Coordinate(10.0, 20.0));

        Report report = Report.builder().id(reportId).location(location).build();
        UUID departmentId = UUID.randomUUID();

        DepartmentOffice office1 = DepartmentOffice.builder().id(UUID.randomUUID()).build();
        DepartmentOffice office2 = DepartmentOffice.builder().id(UUID.randomUUID()).build();

        List<DepartmentOffice> candidates = List.of(office1, office2);

        when(reportRepository.findDepartmentIdByReportId(reportId)).thenReturn(departmentId);
        when(officeRepository.findNearestOffices(departmentId, 20.0, 10.0, 5)).thenReturn(candidates);
        when(routingClient.getDurations(location, candidates)).thenThrow(new RuntimeException("Mapbox down"));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        taskService.autoAssign(report);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());

        Task savedTask = captor.getValue();
        assertEquals(office1.getId(), savedTask.getDepartmentOffice().getId()); // fallback to nearest
    }

    @Test
    void autoAssign_shouldThrow_whenNoCandidates() {
        UUID reportId = UUID.randomUUID();
        Report report = Report.builder()
                .id(reportId)
                .location(new GeometryFactory().createPoint(new Coordinate(0,0)))
                .build();

        UUID departmentId = UUID.randomUUID();
        when(reportRepository.findDepartmentIdByReportId(reportId)).thenReturn(departmentId);
        when(officeRepository.findNearestOffices(departmentId, 0.0, 0.0, 5))
                .thenReturn(Collections.emptyList());

        AppException ex = assertThrows(AppException.class, () -> taskService.autoAssign(report));
        assertEquals(ErrorCode.OFFICE_NOT_FOUND, ex.getErrorCode());
    }
}
