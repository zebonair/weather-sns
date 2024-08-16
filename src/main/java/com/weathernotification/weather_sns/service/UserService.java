package com.weathernotification.weather_sns.service;

import com.weathernotification.weather_sns.exception.ErrorCode;
import com.weathernotification.weather_sns.exception.ServiceException;
import com.weathernotification.weather_sns.model.User;
import com.weathernotification.weather_sns.model.UserVM;
import com.weathernotification.weather_sns.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    @Transactional
    public User getUserById(Long id) {
        if (id == null) {
            throw new ServiceException(ErrorCode.ID_MISSING);
        }

        return userRepository
                .findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND, id));
    }

    @Transactional
    public UserVM updateUser(Long id, User userDetails) {
        if (id == null) {
            throw new ServiceException(ErrorCode.ID_MISSING);
        }

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND, id));

        if (userDetails.getUsername() != null
                && !userDetails.getUsername().equals(user.getUsername())) {
            user.setUsername(userDetails.getUsername());
        }
        if (userDetails.getPassword() != null
                && !userDetails.getPassword().equals(user.getPassword())) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getLocation() != null
                && !userDetails.getLocation().equals(user.getLocation())) {
            user.setLocation(userDetails.getLocation());
        }

        userRepository.save(user);

        return toUserVM(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND, id));

        userRepository.delete(user);
    }

    private UserVM toUserVM(User user) {
        return new UserVM(user.getUsername(), user.getEmail(), user.getLocation());
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new ServiceException(ErrorCode.USER_OBJECT_MISSING);
        }

        if (!StringUtils.hasText(user.getUsername())) {
            throw new ServiceException(ErrorCode.USERNAME_MISSING);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new ServiceException(ErrorCode.EMAIL_MISSING);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new ServiceException(ErrorCode.PASSWORD_MISSING);
        }
        if (!StringUtils.hasText(user.getLocation())) {
            throw new ServiceException(ErrorCode.LOCATION_MISSING);
        }
    }
}