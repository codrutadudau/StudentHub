package com.project.StudentHub.dto;

import java.time.LocalDateTime;

public class QuizDto {
    private int id;

    private String name;

    private String quizIntro;

    private LocalDateTime timeOpen;

    private LocalDateTime timeClose;

    private String password;

    private int course;

    private int duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuizIntro() {
        return quizIntro;
    }

    public void setQuizIntro(String quizIntro) {
        this.quizIntro = quizIntro;
    }

    public LocalDateTime getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(LocalDateTime timeOpen) {
        this.timeOpen = timeOpen;
    }

    public LocalDateTime getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(LocalDateTime timeClose) {
        this.timeClose = timeClose;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
