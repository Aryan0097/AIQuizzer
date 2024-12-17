package com.quizapp.AIQuizzer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quizapp.AIQuizzer.Entity.Attempt;
import com.quizapp.AIQuizzer.Entity.Quiz;
import com.quizapp.AIQuizzer.Request.AttemptAnswerRequest;
import com.quizapp.AIQuizzer.Request.QuizRequest;
import com.quizapp.AIQuizzer.Service.AttemptService;
import com.quizapp.AIQuizzer.Service.EmailNotificationService;
import com.quizapp.AIQuizzer.Service.QuizService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class QuizController {

    private final QuizService quizService;
    private final AttemptService attemptService;
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public QuizController(QuizService quizService, AttemptService attemptService, EmailNotificationService emailNotificationService) {
        this.quizService = quizService;
        this.attemptService = attemptService;
        this.emailNotificationService = emailNotificationService;
    }

    // **1. Generate Quiz**
    @PostMapping("/generate")
    public ResponseEntity<Quiz> generateQuiz(@RequestBody QuizRequest quizRequest) {
        Quiz quiz = quizService.generateQuiz(quizRequest);
        return ResponseEntity.ok(quiz);
    }

    // **2. Submit Quiz**
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<Attempt> submitQuiz(@PathVariable Long quizId, @RequestBody AttemptAnswerRequest answers) {
        Attempt attempt = quizService.submitQuiz(quizId, answers);
        return ResponseEntity.ok(attempt);
    }

    // **3. Retry Quiz**
    @GetMapping("/{quizId}/retry")
    public ResponseEntity<Quiz> retryQuiz(@PathVariable Long quizId) {
        Quiz quiz = quizService.retryQuiz(quizId);
        return ResponseEntity.ok(quiz);
    }

    // **4. Retry Submit**
    @PostMapping("/{quizId}/retrySubmit")
    public ResponseEntity<Attempt> retryQuiz(@PathVariable Long quizId, @RequestBody AttemptAnswerRequest answers) {
        Attempt attempt = quizService.retrySubmit(quizId, answers);
        return ResponseEntity.ok(attempt);
    }

    // **5. Get Quiz History with Filters**
    @GetMapping("/history")
    public ResponseEntity<List<Attempt>> getQuizHistory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String gradeLevel,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Integer minMarks,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<Attempt> attempts = attemptService.getFilteredAttempts(userId, gradeLevel, subject, minMarks, startDate, endDate);
        return ResponseEntity.ok(attempts);
    }

    // **6. Get Hint for Question**
    // @GetMapping("/questions/{questionId}/hint")
    // public ResponseEntity<String> getHint(@PathVariable Long questionId) {
    //     String hint = quizService.getHint(questionId);
    //     return ResponseEntity.ok(hint);
    // }

    // **7. Send Email Notification**
    @PostMapping("/notifications/send")
    public ResponseEntity<String> sendNotification(@RequestParam Long attemptId) {
        String message = emailNotificationService.sendResultsNotification(attemptId);
        return ResponseEntity.ok(message);
    }
}

