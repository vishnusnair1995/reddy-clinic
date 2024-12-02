package com.reddyclinic.doctorportal.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection (if needed)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/register","/api/users/login").permitAll()
                        // Allow public access for registration
                        .anyRequest().authenticated()  // Secure the rest of the API
                )            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Return an instance of BCryptPasswordEncoder
    }
}
