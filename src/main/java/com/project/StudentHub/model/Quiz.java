package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;

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
    @NotEmpty
    @Temporal(TemporalType.TIME)
    private Date timeOpen;

    @Column
    @NotEmpty
    @Temporal(TemporalType.TIME)
    private Date timeClose;

    @Column
    @NotEmpty
    private String password;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "quizzes_questions",
            joinColumns = @JoinColumn(name = "quiz_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id")
    )

    private Collection<Question> questions;

    public Collection<Question> getQuestion() {
        return questions;
    }

    public void setQuestion(Collection<Question> question) {
        this.questions = question;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuizIntro() {
        return quizIntro;
    }

    public Date getTimeOpen() {
        return timeOpen;
    }

    public Date getTimeClose() {
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

    public void setTimeOpen(Date timeOpen) {
        this.timeOpen = timeOpen;
    }

    public void setTimeClose(Date timeClose) {
        this.timeClose = timeClose;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
