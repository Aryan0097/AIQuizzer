package com.quizapp.AIQuizzer.Service;

import com.quizapp.AIQuizzer.Entity.Attempt;
import com.quizapp.AIQuizzer.Entity.User;
import com.quizapp.AIQuizzer.Entity.Quiz;
import com.quizapp.AIQuizzer.Repository.AttemptRepository;
import com.quizapp.AIQuizzer.Repository.UserRepository;
import com.quizapp.AIQuizzer.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final UserRepository userRepository; 
    private final QuizRepository quizRepository; 

    @Autowired
    public AttemptService(AttemptRepository attemptRepository, UserRepository userRepository, QuizRepository quizRepository) {
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public List<Attempt> getFilteredAttempts(Long userId, String gradeLevel, String subject, Integer minScore,
                                             Date startDate, Date endDate) {

        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null); 


        return attemptRepository.findByFilters(
            user != null ? user.getId() : null,  
            subject,                             
            gradeLevel,                          
            minScore,                            
            startDate,                          
            endDate                              
        );
        
    }
}
