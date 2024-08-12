package com.weathernotification.weather_sns.repository;

import com.weathernotification.weather_sns.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
