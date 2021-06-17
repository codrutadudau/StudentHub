package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String studentIdentificationNumber;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="classroom_id")
    private Classroom classroom;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userStudent")
    @JsonIgnore
    private Collection<QuizInstance> quizInstances;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentIdentificationNumber() {
        return studentIdentificationNumber;
    }

    public void setStudentIdentificationNumber(String studentIdentificationNumber) {
        this.studentIdentificationNumber = studentIdentificationNumber;
    }
}
