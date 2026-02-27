package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.report.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    List<Attachment> findByReportId(UUID reportId);
}