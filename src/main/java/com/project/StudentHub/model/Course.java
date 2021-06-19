package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255)
    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Collection<Quiz> quizzes;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "classrooms_courses",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    )
    private Collection<Classroom> classrooms;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserTeacher userTeacher;

    public UserTeacher getUserTeacher() {
        return userTeacher;
    }

    public void setUserTeacher(UserTeacher userTeacher) {
        this.userTeacher = userTeacher;
    }

    public Collection<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Collection<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Collection<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
