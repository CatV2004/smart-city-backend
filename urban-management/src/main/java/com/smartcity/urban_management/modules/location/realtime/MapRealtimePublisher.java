package com.smartcity.urban_management.modules.location.realtime;

import com.smartcity.urban_management.modules.location.dto.MapFeatureResponse;
import com.smartcity.urban_management.modules.location.dto.MapRealtimeMessage;
import com.smartcity.urban_management.modules.location.mapper.MapFeatureMapper;
import com.smartcity.urban_management.modules.report.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapRealtimePublisher {

    private final SimpMessagingTemplate messagingTemplate;
    private final MapFeatureMapper mapFeatureMapper;

    public void created(Report report) {

        log.info(
                "[MapRealtime] Publishing CREATE event | reportId={}",
                report.getId()
        );

        publish(
                "CREATE",
                mapFeatureMapper.toReportFeature(report)
        );
    }

    public void updated(Report report) {

        log.info(
                "[MapRealtime] Publishing UPDATE event | reportId={} | status={}",
                report.getId(),
                report.getStatus()
        );

        publish(
                "UPDATE",
                mapFeatureMapper.toReportFeature(report)
        );
    }

    public void deleted(Report report) {

        log.info(
                "[MapRealtime] Publishing DELETE event | reportId={}",
                report.getId()
        );

        publish(
                "DELETE",
                mapFeatureMapper.toReportFeature(report)
        );
    }

    private void publish(
            String action,
            MapFeatureResponse feature
    ) {

        try {

            Object reportId = feature.getProperties().get("id");

            log.info(
                    "[MapRealtime] Sending websocket message | action={} | reportId={} | destination={}",
                    action,
                    reportId,
                    "/topic/map-data"
            );

            messagingTemplate.convertAndSend(
                    "/topic/map-data",
                    MapRealtimeMessage.builder()
                            .action(action)
                            .feature(feature)
                            .build()
            );

            log.info(
                    "[MapRealtime] Websocket message sent successfully | action={} | reportId={}",
                    action,
                    reportId
            );

        } catch (Exception e) {

            log.error(
                    "[MapRealtime] Failed to send websocket message | action={}",
                    action,
                    e
            );

            throw e;
        }
    }
}