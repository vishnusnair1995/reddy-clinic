package com.reddyclinic.doctorportal.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.ArrayList;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "your_very_long_secure_secret_key_with_at_least_32_characters_your_very_long_secure_secret_key_with_at_least_32_characters";
    private static final String BEARER_PREFIX = "Bearer ";

    private Key getSigningKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();  // Retrieve the user's email (subject)

                if (username != null) {
                    // Set the user details in the security context
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException | IllegalArgumentException e) {
                // If token is invalid, authentication is not set
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}