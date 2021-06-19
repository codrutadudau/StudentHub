package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@Entity
@Table(name = "user_student")
public class UserStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 16, unique = true)
    @NotEmpty
    private String identificationNumber;

    @OneToOne
    @JoinColumn(unique = true, name="user_id", referencedColumnName = "id")
    private Classroom classroom;

    @OneToOne
    @JoinColumn(unique = true, name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userStudent")
    private Collection<QuizInstance> quizInstances;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userStudent")
    private Collection<AnswerOption> answerOptions;

    public Collection<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(Collection<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<QuizInstance> getQuizInstances() {
        return quizInstances;
    }

    public void setQuizInstances(Collection<QuizInstance> quizInstances) {
        this.quizInstances = quizInstances;
    }
}
