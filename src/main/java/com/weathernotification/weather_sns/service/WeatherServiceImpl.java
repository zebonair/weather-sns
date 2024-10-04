package com.weathernotification.weather_sns.service;

import com.weathernotification.weather_sns.config.WeatherAPIConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final String weatherApiKey;
    private final String weatherApiUrlCurrent;
    private final WeatherProducerService weatherProducerService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    public WeatherServiceImpl(
            RestTemplate restTemplate,
            WeatherAPIConfig weatherApiConfig,
            WeatherProducerService weatherProducerService) {
        this.restTemplate = restTemplate;
        this.weatherApiKey = weatherApiConfig.getWeatherApiKey();
        this.weatherApiUrlCurrent = weatherApiConfig.getWeatherApiUrl();
        this.weatherProducerService = weatherProducerService;
    }

    @Override
    public String getWeather(String location) {
        try {
            String url =
                    String.format(
                            "%s?key=%s&q=%s&aqi=no", weatherApiUrlCurrent, weatherApiKey, location);
            logger.info("Successfully fetched weather data for location: {}", location);

            String weatherData = restTemplate.getForObject(url, String.class);
            weatherProducerService.sendWeatherUpdate(weatherData);
            return weatherData;
        } catch (Exception e) {
            logger.error("Error fetching weather data for location: {}", location, e);
            throw e;
        }
    }
}
