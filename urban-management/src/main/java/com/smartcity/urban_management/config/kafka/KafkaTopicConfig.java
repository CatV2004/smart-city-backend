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
}