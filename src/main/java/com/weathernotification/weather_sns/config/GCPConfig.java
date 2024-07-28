package com.weathernotification.weather_sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GCPConfig {

    @Value("${gcp.projectId}")
    private String projectId;

    @Value("${gcp.secretName}")
    private String secretName;

    @Value("${weather.api.url}")
    private String apiUrl;

    public String getProjectId() {
        return projectId;
    }

    public String getSecretName() {
        return secretName;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
