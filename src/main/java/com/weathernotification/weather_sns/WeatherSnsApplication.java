package com.weathernotification.weather_sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WeatherSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSnsApplication.class, args);
    }
}
