package com.weathernotification.weather_sns.service;

import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import com.weathernotification.weather_sns.config.GCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final String weatherApiUrl;
    private final String weatherApiKey;
    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    public WeatherServiceImpl(RestTemplate restTemplate, GCPConfig gcpconfig) throws Exception {
        this.restTemplate = restTemplate;
        this.weatherApiUrl = gcpconfig.getApiUrl();

        String projectId = gcpconfig.getProjectId();
        String secretId = gcpconfig.getSecretName();

        this.weatherApiKey = getSecret(projectId, secretId);
    }

    @Override
    public String getWeather(String location) {
        try {
            String url =
                    String.format("%s?key=%s&q=%s&aqi=no", weatherApiUrl, weatherApiKey, location);
            logger.info("Successfully fetched weather data for location: {}", location);
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            logger.error("Error fetching weather data for location: {}", location, e);
            throw e;
        }
    }

    private String getSecret(String projectId, String secretNameme) throws Exception {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretVersionName secretVersionName =
                    SecretVersionName.of(projectId, secretNameme, "latest");
            AccessSecretVersionRequest request =
                    AccessSecretVersionRequest.newBuilder()
                            .setName(secretVersionName.toString())
                            .build();
            return client.accessSecretVersion(request).getPayload().getData().toStringUtf8();
        }
    }
}
