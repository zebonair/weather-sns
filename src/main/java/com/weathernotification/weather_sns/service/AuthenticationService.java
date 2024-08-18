package com.weathernotification.weather_sns.service;

import com.weathernotification.weather_sns.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired private UserService userService;

    @Autowired private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        User user = userService.getUserByUsername(username);

        return passwordEncoder.matches(password, user.getPassword());
    }
}
