package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.report.entity.ReportStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReportStatusHistoryRepository extends JpaRepository<ReportStatusHistory, UUID> {

    List<ReportStatusHistory> findByReportIdOrderByChangedAtAsc(UUID reportId);

}