package com.smartcity.urban_management.config.async;

import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor(TaskExecutionProperties properties) {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadNamePrefix(
                properties.getThreadNamePrefix());

        executor.setCorePoolSize(
                properties.getPool().getCoreSize());

        executor.setMaxPoolSize(
                properties.getPool().getMaxSize());

        executor.setQueueCapacity(
                properties.getPool().getQueueCapacity());

        executor.initialize();
        return executor;
    }
}