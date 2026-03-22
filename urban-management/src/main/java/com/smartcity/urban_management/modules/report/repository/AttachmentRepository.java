package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    List<Attachment> findByReportId(UUID reportId);

    @Query("""
                SELECT new com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse(
                    a.id,
                    a.report.id,
                    a.fileName,
                    a.fileUrl,
                    a.storageProvider,
                    a.publicId,
                    a.fileType,
                    a.fileSize,
                    a.createdAt
                )
                FROM Attachment a
                WHERE a.report.id = :reportId AND a.deletedAt IS NULL
                ORDER BY a.createdAt ASC
            """)
    List<AttachmentSummaryResponse> findAllByReportId(UUID reportId);

    void deleteByReportId(UUID reportId);

    @Modifying
    @Query("""
                UPDATE Attachment a
                SET a.deletedAt = CURRENT_TIMESTAMP
                WHERE a.report.id = :reportId
                  AND a.deletedAt IS NULL
            """)
    int softDeleteByReportId(UUID reportId);
}