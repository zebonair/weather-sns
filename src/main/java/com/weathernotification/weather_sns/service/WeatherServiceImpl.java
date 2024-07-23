package com.weathernotification.weather_sns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final RestTemplate restTemplate;
    private final String weatherApiUrl;
    private final String weatherApiKey;

    public WeatherServiceImpl(
            RestTemplate restTemplate,
            @Value("${weather.api.url}") String weatherApiUrl,
            @Value("${weather.api.key}") String weatherApiKey) {
        this.restTemplate = restTemplate;
        this.weatherApiUrl = weatherApiUrl;
        this.weatherApiKey = weatherApiKey;
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
}
