package com.weathernotification.weather_sns.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.weather-update}")
    private String topic;

    public WeatherProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWeatherUpdate(String weatherData) {
        kafkaTemplate.send(topic, weatherData);
        System.out.println("Sent weather update: " + weatherData);
    }
}
