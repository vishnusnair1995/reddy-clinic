package com.reddyclinic.doctorportal.config;

import com.reddyclinic.doctorportal.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuring the security filter chain to manage authentication and authorization
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disabling CSRF as we're using JWT for authentication, CSRF is not needed for stateless apps
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/register","/api/users/login").permitAll()
                        .anyRequest().authenticated()  // Secure the rest of the API
                        // Add custom JWT filter before the UsernamePasswordAuthenticationFilter to authenticate requests
                )        .addFilterBefore(new JwtAuthenticationFilter(secretKey), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();

    }
    // Bean for encoding passwords, uses BCrypt which is a secure hashing algorithm
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Return an instance of BCryptPasswordEncoder
    }
}
