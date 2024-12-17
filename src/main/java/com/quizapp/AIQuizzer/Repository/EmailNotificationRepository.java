package com.quizapp.AIQuizzer.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizapp.AIQuizzer.Entity.EmailNotification;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

    List<EmailNotification> findByUserId(Long userId);

    List<EmailNotification> findByEmail(String email);

    List<EmailNotification> findBySentDate(Date sentDate);
}

