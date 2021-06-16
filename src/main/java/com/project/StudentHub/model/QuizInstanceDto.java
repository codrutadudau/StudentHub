package com.project.StudentHub.model;

import java.time.LocalDateTime;

public class QuizInstanceDto {

    private int grade;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer user;

    private Integer quiz;

    private Integer assignedBy;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getQuiz() {
        return quiz;
    }

    public void setQuiz(Integer quiz) {
        this.quiz = quiz;
    }

    public Integer getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Integer assignedBy) {
        this.assignedBy = assignedBy;
    }
}
