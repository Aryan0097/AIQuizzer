package com.quizapp.AIQuizzer.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizapp.AIQuizzer.Entity.Quiz;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Quiz findByQuizId(long quizId);

    List<Quiz> findByGradeLevel(String gradeLevel);

    List<Quiz> findBySubject(String subject);
}
