package com.quizapp.AIQuizzer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.quizapp.AIQuizzer.Entity.Attempt;
import com.quizapp.AIQuizzer.Entity.User;
import com.quizapp.AIQuizzer.Repository.AttemptRepository;

@Service
public class EmailNotificationService {

    private final AttemptRepository attemptRepository;
    private final JavaMailSender mailSender;
    private final AIService AIService;
    private final UserService userService;

    @Autowired
    public EmailNotificationService(AttemptRepository attemptRepository, JavaMailSender mailSender,
                               AIService AIService,UserService userService) {
        this.attemptRepository = attemptRepository;
        this.mailSender = mailSender;
        this.AIService = AIService;
        this.userService = userService;
    }

    public String sendResultsNotification(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
            .orElseThrow(() -> new RuntimeException("Attempt not found"));

        String emailBody = buildEmailBody(attempt);
        User user = userService.getUserById(attempt.getUser().getId());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Quiz Results & Suggestions");
        message.setText(emailBody);

        mailSender.send(message);
        return "Notification sent successfully!";
    }

    private String buildEmailBody(Attempt attempt) {
        return String.format("Hi %s,\n\n" +
                "Your quiz on %s scored %d marks.\n\n" +
                "Suggestions to improve your skills in %s\n\n" +
                "Best regards,\nAI Quizzer Team",
                attempt.getUser().getUsername(),
                attempt.getQuiz().getSubject(),
                attempt.getScore(),
                attempt.getQuiz().getSubject());
    }
}

