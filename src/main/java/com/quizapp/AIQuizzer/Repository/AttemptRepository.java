package com.quizapp.AIQuizzer.Repository;

import com.quizapp.AIQuizzer.Entity.Attempt;
import com.quizapp.AIQuizzer.Entity.Quiz;
import com.quizapp.AIQuizzer.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    List<Attempt> findByUser(User user);

    List<Attempt> findByQuiz(Quiz quiz);

    List<Attempt> findByScoreGreaterThanEqual(Integer score);

    List<Attempt> findByAttemptDate(Date attemptDate);

    List<Attempt> findByAttemptDateBetween(Date from, Date to);

    @Query("SELECT a FROM Attempt a " +
            "WHERE (:userId IS NULL OR a.user.id = :userId) " +
            "AND (:subject IS NULL OR a.quiz.subject = :subject) " +
            "AND (:gradeLevel IS NULL OR a.quiz.gradeLevel = :gradeLevel) " + 
            "AND (:minScore IS NULL OR a.score >= :minScore) " +
            "AND (:startDate IS NULL OR :endDate IS NULL OR a.attemptDate BETWEEN :startDate AND :endDate)")
    List<Attempt> findByFilters(
            @Param("userId") Long userId,
            @Param("subject") String subject,
            @Param("gradeLevel") String gradeLevel,
            @Param("minScore") Integer minScore, 
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
