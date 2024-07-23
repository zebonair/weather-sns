package com.weathernotification.weather_sns.controller;

import com.weathernotification.weather_sns.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(
            @RequestParam(value = "location", required = false) String location) {
        if (location == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Location parameter is required.");
        } else {
            String weatherInfo = weatherService.getWeather(location);
            return ResponseEntity.ok(weatherInfo);
        }
    }
}
