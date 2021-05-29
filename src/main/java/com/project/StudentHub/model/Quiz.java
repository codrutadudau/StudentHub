package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Data;

@Data
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    @NotEmpty
    private String name;

    @Column(length = 200)
    @NotEmpty
    private String quizIntro;

    @Column
    private LocalDateTime timeOpen;

    @Column
    private LocalDateTime timeClose;

    @Column
    @NotEmpty
    private String password;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "quizzes_questions",
            joinColumns = @JoinColumn(name = "quiz_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id")
    )

    private Collection<Question> questions;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuizIntro() {
        return quizIntro;
    }

    public LocalDateTime getTimeOpen() {
        return timeOpen;
    }

    public LocalDateTime getTimeClose() {
        return timeClose;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuizIntro(String quizIntro) {
        this.quizIntro = quizIntro;
    }

    public void setTimeOpen(LocalDateTime timeOpen) {
        this.timeOpen = timeOpen;
    }

    public void setTimeClose(LocalDateTime timeClose) {
        this.timeClose = timeClose;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> question) {
        this.questions = question;
    }

    public void add(Question question) {
        this.questions.add(question);
    }

    public void remove(Question question) {
        this.questions.remove(question);
    }
}
