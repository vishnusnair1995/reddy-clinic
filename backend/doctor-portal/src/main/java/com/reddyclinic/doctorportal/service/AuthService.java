package com.reddyclinic.doctorportal.service;

import com.reddyclinic.doctorportal.dto.LoginRequest;
import com.reddyclinic.doctorportal.dto.LoginResponse;
import com.reddyclinic.doctorportal.entity.Role;
import com.reddyclinic.doctorportal.entity.User;
import com.reddyclinic.doctorportal.exception.InvalidCredentialException;
import com.reddyclinic.doctorportal.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.secret.key}")
    private String secretKey;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Replace with a secure key
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*  Method to authenticate a user and return a
     *  JWT token if credentials are valid
     */
    public LoginResponse authenticateUser(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidCredentialException("Invalid email or password"));
        // Validate user existence
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Invalid email or password");
        }

        String token = generateToken(user.getEmail(), user.getRoles());
        log.info("User {} logged in successfully", request.getEmail());
        // Return user details along with the generated token
        return new LoginResponse(user.getName(), user.getId(), user.getEmail(), token);
    }

    private String generateToken(String email, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().map(Role::getRoleName).collect(Collectors.toList()));
        // Building the JWT with claims, subject (email), issued and expiration times
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() +jwtExpiration )) // 1-day validity
                .signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }

    //retrieves the signing key for JWT (in a real-world app, this should be stored securely, not hardcoded)
    private Key getSigningKey() {
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }
}
