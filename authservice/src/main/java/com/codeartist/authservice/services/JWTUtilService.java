package com.codeartist.authservice.services;

import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtilService {
    private final String  SECRET_KEY = "z0rV7IK0R7MuWo70kRxJpxmd9zI2AnproZ17KbOYg3e";

    SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    public String generateToken(UserDto userDto){
        return generateClaims(userDto,30);
    }

    public String generateRefreshToken(UserDto userDto){
        return generateClaims(userDto,60*24*7);
    }

    private String generateClaims(UserDto claim,int expirationTime){

        Map<String, String> map = new HashMap<>();
        map.put("userName",claim.getUsername());

     return   Jwts.builder().claims(map).signWith(key)
                 .issuedAt(Date.from(Instant.now()))
                 .expiration(Date.from(Instant.now().plus(expirationTime, ChronoUnit.MINUTES)))
                 .subject(  claim.getUserEmailId() )
                 .compact();
    }


    private Claims getClaims(String token){
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }


    public Boolean isTokenValid(String token,UserDto userDto){
        String userName = getUsername(token);
        return !isTokenExpired(token)
                && (userName.equalsIgnoreCase(userDto.getUsername()));
    }


    public Boolean isTokenExpired(String token){
        Date expirationDate = getExpirationDate(token);
        return expirationDate.toInstant().isBefore(Instant.now());
    }

    public Date getExpirationDate(String token){
        Claims  claims =  getClaims(token);
        return claims.getExpiration();
    }

    public  String getUsername (String token){
        Claims claims = getClaims(token);
        return (String)claims.get("userName");
    }
}
