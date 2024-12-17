package com.quizapp.AIQuizzer.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizapp.AIQuizzer.Entity.Question;

import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuiz_QuizId(Long quizId);

}
