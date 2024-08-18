package com.weathernotification.weather_sns.config;

import com.weathernotification.weather_sns.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.csrf(httpSecurityCsrfConfigurer -> {httpSecurityCsrfConfigurer.disable();});
    //     http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

    //     http.httpBasic(Customizer.withDefaults());

    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(
                httpSecurityCsrfConfigurer -> {
                    httpSecurityCsrfConfigurer.disable();
                });
        return http.authorizeHttpRequests(
                        registry -> {
                            registry.requestMatchers(
                                            "/users/register", "/auth/login", "/auth/logout")
                                    .permitAll();
                            registry.anyRequest().authenticated();
                        })
                .formLogin(
                        formLogin ->
                                formLogin
                                        .loginProcessingUrl("/auth/login")
                                        .defaultSuccessUrl("/home", true))
                .logout(
                        logout ->
                                logout.logoutUrl("/auth/logout")
                                        .logoutSuccessUrl("/auth/login?logout"))
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(
                                        SessionCreationPolicy.ALWAYS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
}
