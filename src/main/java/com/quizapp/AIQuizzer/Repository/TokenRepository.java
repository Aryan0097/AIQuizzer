package com.quizapp.AIQuizzer.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizapp.AIQuizzer.Entity.Token;



@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByUserId(Long userId);
}

