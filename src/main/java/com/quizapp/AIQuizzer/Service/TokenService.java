package com.quizapp.AIQuizzer.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.quizapp.AIQuizzer.Entity.Token;
import com.quizapp.AIQuizzer.Entity.User;
import com.quizapp.AIQuizzer.Repository.TokenRepository;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;





@Service
public class TokenService {
    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    private TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String createRefreshToken(User user) {
        Token token = tokenRepository.findByUserId(user.getId());
        if(token == null) {
            token =	new Token();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());  
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds))); 
        tokenRepository.save(token);
        return token.getToken();
    }

    public Token getByUser(Long userId) {
        return tokenRepository.findByUserId(userId);
    }

    public boolean isRefreshExpired(Token token) {  
        return token.getExpiryDate().before(new Date());
    }
}