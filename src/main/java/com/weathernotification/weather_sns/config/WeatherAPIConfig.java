package com.weathernotification.weather_sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherAPIConfig {
    @Value("${weather-api.key}")
    private String apiKey;

    @Value("${weather-api.url.current}")
    private String apiUrlCurrent;

    public String getWeatherApiKey() {
        return apiKey;
    }

    public String getWeatherApiUrl() {
        return apiUrlCurrent;
    }
}
