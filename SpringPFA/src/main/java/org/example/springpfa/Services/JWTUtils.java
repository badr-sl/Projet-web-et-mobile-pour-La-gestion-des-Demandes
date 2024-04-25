package org.example.springpfa.Services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.springpfa.entities.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JWTUtils {
    private SecretKey secretKey;
    private static final long ExpirationTime =  50_000 ; // 3_600_000 milliseconds in 1 hour #to test use 5_000

    public JWTUtils(){
        // Hardcoded secret string (should be kept secure and not hardcoded in real-world scenarios)
        String secretString = "5454878c454sc54s575c7s5c4s5c4c5s75cs5c7sc4s5c4s54cs57s57scs5754151sc";

        // Decode the secret string from Base64 encoding into raw bytes
        byte[] keyBytes = Base64.getDecoder().decode((secretString.getBytes(StandardCharsets.UTF_8)));

        // Create a SecretKeySpec object using the decoded key bytes and the HmacSHA256 algorithm
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }


    public String generateToken(User user){
        // Start building the JWT token using the Jwts.builder() method
        return Jwts.builder()
                // Set the subject of the token to the username of the user
                .setSubject(user.getUsername())
                // Set the issued date of the token to the current time plus the expiration time
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ExpirationTime))
                // Sign the token with the secret key specified in the JWTUtils class
                .signWith(secretKey)
                // Generate the compact serialized form of the JWT token
                .compact();
    }
    public String refreshToken(HashMap<String, Object> claims , User user){
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()) )
                .setExpiration(new Date(System.currentTimeMillis() + ExpirationTime))
                .signWith(secretKey)
                .compact();
    }
    //
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }
    //
    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        if (claims == null) {
            throw new IllegalArgumentException("Claims cannot be null");
        }
        return claimsTFunction.apply(claims);
    }

    public Date extractExpiration(String token) {
        Date claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt();
        System.out.println("claims.getExpiration()");
        System.out.println(claims);
        Date dateExpiration = new Date(claims.getTime()+ExpirationTime);

        return dateExpiration;
        //return claims.getExpiration();
    }

    public boolean isTokenValid(String token, User user){
        final String userName = extractUserName(token);
        System.out.println(userName);
        System.out.println((userName.equals(user.getUsername())) && !isTokenExpired(token));
        return (userName.equals(user.getUsername())) && !isTokenExpired(token);
    }

    public  boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
        //return extractClaims(token,Claims::getExpiration).before(new Date());
        //return false;
    }




}

