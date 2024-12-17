package com.quizapp.AIQuizzer.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.quizapp.AIQuizzer.Entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}

