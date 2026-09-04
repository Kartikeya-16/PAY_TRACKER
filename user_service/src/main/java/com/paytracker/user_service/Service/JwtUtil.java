package com.paytracker.user_service.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key used to sign every token. Same key is needed to verify it later.
    private final SecretKey key = Keys.hmacShaKeyFor(
            "paytracker-super-secret-key-that-is-long-enough-1234".getBytes()
    );

    // Creates a token proving "this is userId X", valid for 24 hours.
    public String generateToken(Long userId, String username) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }

    // Reads a token and returns the userId inside it.
    // Throws an error automatically if the token is invalid or expired.
    public Long extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }
}