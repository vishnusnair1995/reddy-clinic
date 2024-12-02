package com.reddyclinic.doctorportal.util;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class JwtUtlis {

    private static final String SECRET_KEY = "your_very_long_secure_secret_key_with_at_least_32_characters_your_very_long_secure_secret_key_with_at_least_32_characters";
    private static final String BEARER_PREFIX = "Bearer ";

    private Key getSigningKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

}
