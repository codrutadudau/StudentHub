package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@Entity
@Table(name = "classroom")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 2)
    @NotEmpty
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int year;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classroom")
    @JsonIgnore
    private Collection<UserStudent> userStudents;

    @ManyToMany(mappedBy = "classrooms")
    @JsonIgnore
    private Collection<Course> courses;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Collection<UserStudent> getUserStudents() {
        return userStudents;
    }

    public void setUserStudents(Collection<UserStudent> userStudents) {
        this.userStudents = userStudents;
    }

    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> courses) {
        this.courses = courses;
    }
}
