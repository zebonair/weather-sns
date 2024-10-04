package com.weathernotification.weather_sns.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WeatherConsumerService {

    @KafkaListener(topics = "weather-update", groupId = "weather-notification-service")
    public void listenWeatherUpdates(String message) {
        System.out.println("Received weather update: " + message);
    }
}
