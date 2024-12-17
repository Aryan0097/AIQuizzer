package com.quizapp.AIQuizzer.Security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {      
                                                   

    @Value("${AIQuizzer.app.secret}")                                            
    private String APP_SECRET ;

    @Value("${AIQuizzer.expires.in}")
    private long EXPIRES_IN;                                                        

    private Key getKey() {
        return Keys.hmacShaKeyFor(APP_SECRET.getBytes());
    }


    public String generateJWTToken(Authentication auth){                           
        JWTUserDetails userDetails = (JWTUserDetails) auth.getPrincipal();        
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);             
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();      
    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact(); 
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            // Handle invalid signature
            return false;
        } catch (MalformedJwtException e) {
            // Handle malformed JWT
            return false;
        } catch (ExpiredJwtException e) {
            // Handle expired JWT
            return false;
        } catch (UnsupportedJwtException e) {
            // Handle unsupported JWT
            return false;
        } catch (IllegalArgumentException e) {
            // Handle illegal argument
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }


}