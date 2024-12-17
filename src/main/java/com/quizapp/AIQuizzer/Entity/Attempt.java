package com.quizapp.AIQuizzer.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Linking the user entity (not userId directly)

    private int score;

    private boolean isRetry;

    @Temporal(TemporalType.TIMESTAMP) // Use TIMESTAMP if you need both date and time
    private Date attemptDate;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttemptAnswer> answers;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public List<AttemptAnswer> getAnswers() {
        return answers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRetry(boolean isRetry) {
        this.isRetry = isRetry;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setAnswers(List<AttemptAnswer> answers) {
        this.answers = answers;
    }

}
