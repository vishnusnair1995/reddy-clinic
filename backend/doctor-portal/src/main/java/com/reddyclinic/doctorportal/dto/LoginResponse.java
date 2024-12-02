package com.reddyclinic.doctorportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long userId;            // The unique ID of the user
    private String name;            // The user's full name
    private String email;           // The user's email address
    private String token;           // The JWT or authentication token
    private Instant tokenExpiry;    // Expiration timestamp of the token
    private String role;// Role of the user (e.g., admin, user)

    public LoginResponse(String name, Long userId, String email, String token) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.token = token;
    }
}
