package com.codeartist.gatewayservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final SecretKey secretKey ;
    public JwtUtils (@Value("${secret.key:z0rV7IK0R7MuWo70kRxJpxmd9zI2AnproZ17KbOYg3e}")String secretKey){
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Boolean validateToken(String token){
        try {
            getClaimsFrmToken(token);
            return true;
        }
        catch (JwtException  | IllegalArgumentException e){
//            throw new RuntimeException(e);
            System.out.println("Unable to validate the token"+e.getMessage());
            return  false;
        }

    }
    public Claims getClaimsFrmToken(String token) {
      return  Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
    public String getUsernameFrmToken(String token){
        try{
            return (String)getClaimsFrmToken(token).get("username",String.class);
        }
            catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public Integer getUserIdFrmToken(String token){
        try{
            return Integer.parseInt(getClaimsFrmToken(token).get("user",String.class));
        }
            catch (Exception e){
            throw new RuntimeException(e);
        }
    }
//    public Date getExpirationDate(String token){
//        Claims  claims =  getClaimsFrmToken(token);
//        return claims.getExpiration();
//    }
//    public  Boolean isTokenExpired(String token){
//        Date exDate = getExpirationDate(token);
//        return exDate.toInstant().isBefore(Instant.now());
//    }

}
