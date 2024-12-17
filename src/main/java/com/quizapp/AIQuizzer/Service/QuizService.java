package com.quizapp.AIQuizzer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizapp.AIQuizzer.Entity.Attempt;
import com.quizapp.AIQuizzer.Entity.Question;
import com.quizapp.AIQuizzer.Entity.Quiz;
import com.quizapp.AIQuizzer.Repository.AttemptRepository;
import com.quizapp.AIQuizzer.Repository.QuestionRepository;
import com.quizapp.AIQuizzer.Repository.QuizRepository;
import com.quizapp.AIQuizzer.Repository.UserRepository;
import com.quizapp.AIQuizzer.Request.AttemptAnswerRequest;
import com.quizapp.AIQuizzer.Request.QuizRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final AIService aiService;


    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository,
                       AttemptRepository attemptRepository, AIService aiService,UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.aiService = aiService;
        this.userRepository=userRepository;
    }

    public Quiz generateQuiz(QuizRequest quizRequest) {
        Quiz quiz = aiService.generateQuiz(quizRequest);
        return quizRepository.save(quiz);
    }

    public Attempt submitQuiz(Long quizId, AttemptAnswerRequest answers) {
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
        Attempt attempt = evaluateQuiz(quiz, answers);
        return attemptRepository.save(attempt);
    }

    public Quiz retryQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
        return quiz;
    }

    public Attempt retrySubmit(Long quizId, AttemptAnswerRequest answers) {
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
        Attempt attempt = evaluateQuiz(quiz, answers);
        attempt.setRetry(true); 
        return attemptRepository.save(attempt);
    }

    private Attempt evaluateQuiz(Quiz quiz, AttemptAnswerRequest attemptAnswerRequest) {
        Attempt attempt = new Attempt();
        // attempt.setUser(userRepository.findById(attemptAnswerRequest.getUserId()));
        attempt.setQuiz(quiz);
        int score = 0;
    
        for (Map.Entry<Long, String> entry : attemptAnswerRequest.getAnswers().entrySet()) {
            Long questionId = entry.getKey(); 
            String userAnswer = entry.getValue();
    
            Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found for ID: " + questionId));
    
            if (question.getCorrectAnswer().equalsIgnoreCase(userAnswer.trim())) {
                score++;
            }
        }
    
        attempt.setScore(score);
        attempt.setAttemptDate(new Date());
    
        return attempt;
    }
    

    public String getHint(Long questionId) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question not found"));
        return question.getHint();
    }
}
