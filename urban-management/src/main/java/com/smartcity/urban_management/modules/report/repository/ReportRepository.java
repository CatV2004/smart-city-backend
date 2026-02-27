package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
}