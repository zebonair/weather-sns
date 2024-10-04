package com.weathernotification.weather_sns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherProducerService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.weather-update}")
    private String topic;

    public WeatherProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWeatherUpdate(String weatherData) {
        kafkaTemplate.send(topic, weatherData);
        logger.info("Sending weather update: {}", weatherData);
    }
}
