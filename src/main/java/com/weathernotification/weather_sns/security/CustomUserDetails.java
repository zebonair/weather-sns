package com.weathernotification.weather_sns.security;

import com.weathernotification.weather_sns.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final String adminUsername;
    private final User user;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetails.class);

    public CustomUserDetails(User user, String adminUsername) {
        this.user = user;
        this.adminUsername = adminUsername;
        logRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> adminUsernamesSet =
                Stream.of(adminUsername.split(",")).map(String::trim).collect(Collectors.toSet());

        if (adminUsername.contains(user.getUsername())) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    private void logRoles() {
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        logger.info(
                "User roles: {}",
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", ")));
    }
}
