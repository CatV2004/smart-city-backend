package com.smartcity.urban_management.config.kafka;

import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic reportCreatedTopic() {
        return TopicBuilder.name(KafkaTopics.REPORT_CREATED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name(KafkaTopics.NOTIFICATION_SEND)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic reportAttachmentsAddedTopic() {
        return TopicBuilder.name(KafkaTopics.REPORT_ATTACHMENTS_ADDED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic reportAIAnalyzedTopic() {
        return TopicBuilder.name(KafkaTopics.REPORT_AI_ANALYZED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic reportStatusChanged() {
        return TopicBuilder.name(KafkaTopics.REPORT_STATUS_CHANGED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic reportInProgress() {
        return TopicBuilder.name(KafkaTopics.REPORT_IN_PROGRESS)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskAssigned() {
        return TopicBuilder.name(KafkaTopics.TASK_ASSIGNED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskStarted() {
        return TopicBuilder.name(KafkaTopics.TASK_STARTED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskCompleted() {
        return TopicBuilder.name(KafkaTopics.TASK_COMPLETED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}