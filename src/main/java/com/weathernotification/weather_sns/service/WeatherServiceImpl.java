package com.weathernotification.weather_sns.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final String weatherApiUrl;
    private final String weatherApiKey;

    public WeatherServiceImpl(RestTemplate restTemplate, 
                              @Value("${weather.api.url}") String weatherApiUrl, 
                              @Value("${weather.api.key}") String weatherApiKey) {
        this.restTemplate = restTemplate;
        this.weatherApiUrl = weatherApiUrl;
        this.weatherApiKey = weatherApiKey;
    }

    @Override
    public String getWeather(String location) {
        String url = String.format("%s?key=%s&q=%s&aqi=no", weatherApiUrl, weatherApiKey, location);
        return restTemplate.getForObject(url, String.class);
    }
}