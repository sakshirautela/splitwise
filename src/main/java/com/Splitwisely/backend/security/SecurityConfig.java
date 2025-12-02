package com.Splitwisely.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final UserDetailsPasswordService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsPasswordService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Create an instance of DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider((UserDetailsService) userDetailsService);

        // Set the UserDetailsService to fetch user details from your source (e.g., database)
        // Set the PasswordEncoder to validate passwords
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsPasswordService(userDetailsService);
        // Return the configured provider
        return provider;
    }

    // You will typically also need a SecurityFilterChain bean to configure HTTP security rules
    // For example:
    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults()); // Use default form login settings or configure custom ones
            // Add configuration for CSRF, sessions, etc.

        return http.build();
    }
    */
}
