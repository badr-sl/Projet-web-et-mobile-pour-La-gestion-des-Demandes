package org.example.springpfa.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.springpfa.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class PasswordEncoderService {

    private final BCryptPasswordEncoder passwordEncoder;
    private Key jwtSecret;

    public PasswordEncoderService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);

    }
    public boolean matches(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    public String generateToken(User user) {
        // Set the expiration time for the token (e.g., 1 hour from now)
        long expirationTime = System.currentTimeMillis() + 3600000; // 1 hour

        // Generate the JWT token
        String token = Jwts.builder()
                .setSubject(user.getEmail()) // Set the subject (email) of the token
                .setIssuedAt(new Date()) // Set the issued date of the token
                .setExpiration(new Date(expirationTime)) // Set the expiration date of the token
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Sign the token with your secret key
                .compact();

        return token;
    }
}
