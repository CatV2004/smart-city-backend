package com.smartcity.urban_management.modules.report.infrastructure.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.urban_management.modules.report.messaging.ReportAIAnalyzedMessage;
import com.smartcity.urban_management.modules.report.service.ReportAIService;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportAIConsumer {

    private final ReportAIService reportAIService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopics.REPORT_AI_ANALYZED,
            groupId = "report-group"
    )
    public void handleAIResult(ConsumerRecord<String, Object> record, Acknowledgment ack) {

        try {
            Object value = record.value();

            if (!(value instanceof Map)) {
                log.warn("[Kafka] Unexpected message type: {}", value.getClass());
                ack.acknowledge();
                return;
            }

            Map<String, Object> map = (Map<String, Object>) value;

            String type = (String) map.get("type");
            if (type == null) {
                log.warn("[Kafka] Missing type field | value={}", map);
                ack.acknowledge();
                return;
            }

            Object messageObj;

            // Map type → class
            switch (type) {
                case "ReportAIAnalyzedMessage":
                    messageObj = objectMapper.convertValue(map, ReportAIAnalyzedMessage.class);
                    break;
                // case khác nếu bạn muốn support nhiều message type
                // case "AnotherMessage":
                //     messageObj = objectMapper.convertValue(map, AnotherMessage.class);
                //     break;
                default:
                    log.warn("[Kafka] Unknown type={} | value={}", type, map);
                    ack.acknowledge();
                    return;
            }

            ReportAIAnalyzedMessage message = (ReportAIAnalyzedMessage) messageObj;

            log.info("📩 Received AI result: reportId={}", message.getReportId());

            reportAIService.processAIResult(message);

            ack.acknowledge();

        } catch (Exception e) {
            log.error("❌ Failed processing AI result", e);
            ack.acknowledge();
        }
    }
}