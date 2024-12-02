package com.reddyclinic.doctorportal.service;

import com.reddyclinic.doctorportal.dto.LoginRequest;
import com.reddyclinic.doctorportal.entity.User;
import com.reddyclinic.doctorportal.exception.InvalidCredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reddyclinic.doctorportal.dto.LoginRequest;
import com.reddyclinic.doctorportal.dto.LoginResponse;
import com.reddyclinic.doctorportal.entity.User;

import com.reddyclinic.doctorportal.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


import java.util.Date;
import java.util.Optional;

@Service
    public class AuthService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        private static final String SECRET_KEY = "your_very_long_secure_secret_key_with_at_least_32_characters_your_very_long_secure_secret_key_with_at_least_32_characters";
        // Replace with a secure key

    private Key getSigningKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }


    public LoginResponse authenticateUser(LoginRequest request) {
            // Validate credentials
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
                throw new InvalidCredentialException("Invalid email or password");
            }
            User user = optionalUser.get();

            // Generate JWT token
            String token = generateToken(user.getEmail());
            return new LoginResponse(user.getName(), user.getUserId(),user.getEmail(), token);
        }

        private String generateToken(String email) {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1-day validity
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        }
}
