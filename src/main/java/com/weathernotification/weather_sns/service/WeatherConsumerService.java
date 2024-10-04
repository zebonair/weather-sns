package com.weathernotification.weather_sns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WeatherConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherConsumerService.class);

    @KafkaListener(topics = "weather-update", groupId = "weather-notification-service")
    public void listenWeatherUpdates(String weatherData) {
        logger.info("Received weather update: {}", weatherData);
    }
}
