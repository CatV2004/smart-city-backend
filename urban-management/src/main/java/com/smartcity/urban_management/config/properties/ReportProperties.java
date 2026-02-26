package com.smartcity.urban_management.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.report")
@Getter
@Setter
public class ReportProperties {

    private boolean autoApprove;
}
