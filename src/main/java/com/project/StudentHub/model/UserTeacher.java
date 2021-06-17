package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "user_teacher")
public class UserTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "userTeacher")
    @JsonIgnore
    private Collection<Course> courses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedBy")
    @JsonIgnore
    private Collection<QuizInstance> quizInstances;

    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
