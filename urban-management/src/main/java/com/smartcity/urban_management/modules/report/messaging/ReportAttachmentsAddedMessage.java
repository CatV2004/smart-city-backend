package com.smartcity.urban_management.modules.report.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAttachmentsAddedMessage {

    UUID reportId;
    List<String> attachmentUrls;
}