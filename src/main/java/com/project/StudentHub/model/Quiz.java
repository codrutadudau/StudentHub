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

    @Column(columnDefinition = "varchar(255) default null")
    @NotEmpty
    private String name;

    @Column(columnDefinition = "text default null")
    @NotEmpty
    private String quizIntro;

    @Column
    private LocalDateTime timeOpen;

    @Column
    private LocalDateTime timeClose;

    @Column
    private String password;

    @Column
    private int duration;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    @JsonIgnore
    private Collection<QuizInstance> quizInstances;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

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

    public Collection<QuizInstance> getQuizInstances() {
        return quizInstances;
    }

    public void setQuizInstances(Collection<QuizInstance> quizInstances) {
        this.quizInstances = quizInstances;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
