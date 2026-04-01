package com.smartcity.urban_management.modules.task.service.impl;

import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import com.smartcity.urban_management.modules.department.repository.DepartmentOfficeRepository;
import com.smartcity.urban_management.modules.location.client.RoutingClient;
import com.smartcity.urban_management.modules.notification.entity.Notification;
import com.smartcity.urban_management.modules.notification.entity.NotificationType;
import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.modules.report.dto.detail.ReportStaffDetailResponse;
import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.mapper.AttachmentMapper;
import com.smartcity.urban_management.modules.report.mapper.ReportDetailMapper;
import com.smartcity.urban_management.modules.report.repository.AttachmentRepository;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.modules.task.dto.*;
import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import com.smartcity.urban_management.modules.task.mapper.EvidenceMapper;
import com.smartcity.urban_management.modules.task.mapper.TaskDetailMapper;
import com.smartcity.urban_management.modules.task.mapper.TaskSummaryMapper;
import com.smartcity.urban_management.modules.task.pagination.TaskSortField;
import com.smartcity.urban_management.modules.task.repository.TaskEvidenceRepository;
import com.smartcity.urban_management.modules.task.repository.TaskRepository;
import com.smartcity.urban_management.modules.task.service.TaskEvidenceService;
import com.smartcity.urban_management.modules.task.service.TaskService;
import com.smartcity.urban_management.modules.task.validator.TaskValidator;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.messaging.event.TaskAssignedEvent;
import com.smartcity.urban_management.shared.messaging.event.TaskCompletedEvent;
import com.smartcity.urban_management.shared.messaging.event.TaskStartedEvent;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final DepartmentOfficeRepository officeRepository;
    private final TaskSortField taskSortField = new TaskSortField();
    private final AttachmentRepository attachmentRepository;
    private final RoutingClient routingClient;
    private final ReportService reportService;
    private final TaskEvidenceService taskEvidenceService;
    private final TaskValidator taskValidator;
    private final TaskEvidenceRepository evidenceRepository;
    private final NotificationService notificationService;
    private static final int CANDIDATE_LIMIT = 5;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UUID autoAssign(Report report) {
        UUID departmentId = reportRepository.findDepartmentIdByReportId(report.getId());

        double lat = report.getLocation().getY();
        double lng = report.getLocation().getX();

        List<DepartmentOffice> candidates =
                officeRepository.findNearestOffices(
                        departmentId,
                        lat,
                        lng,
                        CANDIDATE_LIMIT
                );

        if (candidates.isEmpty()) {
            throw new AppException(ErrorCode.OFFICE_NOT_FOUND);
        }

        DepartmentOffice bestOffice;

        try {
            List<Double> durations =
                    routingClient.getDurations(report.getLocation(), candidates);

            double min = Double.MAX_VALUE;
            bestOffice = candidates.get(0);

            for (int i = 1; i < durations.size(); i++) {
                Double d = durations.get(i);

                if (d != null && d < min) {
                    min = d;
                    bestOffice = candidates.get(i - 1);
                }
            }

        } catch (Exception e) {
            log.warn("Mapbox failed → fallback to nearest (PostGIS)", e);

            bestOffice = candidates.get(0);
        }

        Task task = Task.builder()
                .report(report)
                .departmentOffice(bestOffice)
                .status(TaskStatus.ASSIGNED)
                .assignedAt(LocalDateTime.now())
                .build();

        Task saved = taskRepository.save(task);
        log.info("Assigned report {} → office {}", report.getId(), bestOffice.getId());
        eventPublisher.publishEvent(
                new TaskAssignedEvent(
                        saved.getId(),
                        report.getId(),
                        bestOffice.getId(),
                        bestOffice.getName()
                )
        );

//        notificationService.notifyAdminsTaskAssigned(
//                report.getId(),
//                bestOffice.getId(),
//                bestOffice.getName()
//        );
        return saved.getId();
    }

    @Override
    public PageResponse<TaskSummaryResponse> getAll(
            TaskFilterRequest filter,
            PageRequestDto request,
            CustomUserDetails user
    ) {

        // ===== 1. RESOLVE OFFICE ID (RBAC) =====
        UUID officeId = resolveOfficeId(filter, user);

        // ===== 2. CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, taskSortField);

        // ===== 3. NORMALIZE KEYWORD =====
        String keyword = normalizeKeyword(filter.getKeyword());

        // ===== 4. QUERY =====
        Page<TaskProjection> pageData = taskRepository.searchProjection(
                officeId,
                filter.getStatus(),
                keyword,
                pageable
        );

        // ===== 5. MAP =====
        Page<TaskSummaryResponse> content = pageData.map(TaskSummaryMapper::toSummary);

        return PageMapper.toResponse(content);
    }

    @Override
    public TaskDetailResponse getDetail(UUID id) {

        TaskDetailProjection p = taskRepository.findDetail(id)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        List<AttachmentSummaryResponse> attachments =
                attachmentRepository.findByReportId(p.getReportId())
                        .stream()
                        .map(AttachmentMapper::toResponse)
                        .collect(Collectors.toList());

        ReportStaffDetailResponse report =
                ReportDetailMapper.fromTaskProjection(p, attachments);

        List<EvidenceDto> evidences =
                EvidenceMapper.toDtoList(evidenceRepository.findByTaskId(p.getId()));

        return TaskDetailMapper.fromProjection(p, report, evidences);
    }

    @Override
    @Transactional
    public Report startTask(UUID taskId, CustomUserDetails user) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        taskValidator.validateStartTask(task, user.getId());

        User currentUser = userRepository.getReferenceById(user.getId());

        task.setAssignedUser(currentUser);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setStartedAt(LocalDateTime.now());

        taskRepository.save(task);

        Report report = task.getReport();

        reportService.updateStatus(
                report,
                ReportStatus.IN_PROGRESS,
                "SYSTEM",
                "Task started"
        );

        eventPublisher.publishEvent(
                new TaskStartedEvent(
                        task.getId(),
                        report.getId(),
                        currentUser.getId(),
                        currentUser.getFullName()
                )
        );

        return report;
    }

    @Override
    @Transactional
    public Report completeTask(UUID taskId, CompleteTaskRequest request, CustomUserDetails user) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        String note = request.getNote();

        taskValidator.validateCompleteTask(task, user.getId(), note);

        taskEvidenceService.uploadEvidences(task, request.getFiles());

        task.setStatus(TaskStatus.COMPLETED);
        task.setNote(note);
        task.setCompletedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        Report report = task.getReport();

        reportService.updateStatus(
                report,
                ReportStatus.RESOLVED,
                "SYSTEM",
                note
        );

        eventPublisher.publishEvent(
                new TaskCompletedEvent(
                        task.getId(),
                        report.getId(),
                        user.getId(),
                        task.getAssignedUser().getFullName(),
                        note
                )
        );

        return report;
    }

    // ================= HELPER =================
    public UUID resolveOfficeId(TaskFilterRequest filter, CustomUserDetails user) {

        // ===== ADMIN =====
        if (user.hasRole("ADMIN")) {
            return filter.getDepartmentOfficeId();
        }

        // ===== STAFF =====
        if (user.hasRole("STAFF")) {

            UUID userOfficeId = userRepository.findDepartmentOfficeIdByUserId(user.getId());

            if (userOfficeId == null) {
                throw new AppException(ErrorCode.OFFICE_REQUIRED);
            }

            if (filter.getDepartmentOfficeId() != null &&
                    !filter.getDepartmentOfficeId().equals(userOfficeId)) {
                throw new AppException(ErrorCode.TASK_NOT_IN_SAME_OFFICE);
            }

            return userOfficeId;
        }

        throw new AppException(ErrorCode.FORBIDDEN);
    }

    public String normalizeKeyword(String keyword) {
        if (keyword == null) return null;
        keyword = keyword.trim();
        return keyword.isEmpty() ? null : keyword;
    }
}
