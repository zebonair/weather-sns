package com.weathernotification.weather_sns.service;

import com.weathernotification.weather_sns.exception.ErrorCode;
import com.weathernotification.weather_sns.exception.ServiceException;
import com.weathernotification.weather_sns.model.User;
import com.weathernotification.weather_sns.model.UserVM;
import com.weathernotification.weather_sns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserVM createUser(User user) {
        validateUser(user);
        hashPassword(user);
        userRepository.save(user);

        return toUserVM(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        if (id == null) {
            throw new ServiceException(ErrorCode.ID_MISSING);
        }
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND, id));
    }

    @Transactional(readOnly = true)
    public UserVM getUserByUsername(String username) {
        if (username == null) {
            throw new ServiceException(ErrorCode.USERNAME_MISSING);
        }
        authorizeUserAccess(username);
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new ServiceException(
                                                ErrorCode.USER_NOT_FOUND_USERNAME, username));
        return toUserVM(user);
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
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            hashPassword(userDetails);
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

    private void hashPassword(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
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

    private void authorizeUserAccess(String requestUser) {
        String currentUser = getCurrentUser();

        if (!requestUser.equals(currentUser)) {
            throw new ServiceException(ErrorCode.ACCESS_DENIED);
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new ServiceException(ErrorCode.ACCESS_DENIED);
    }
}
