package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    @NotEmpty
    @Size(min = 2, message = "First Name should have at lest 2 characters")
    private String firstName;

    @Column(length = 100)
    @NotEmpty
    @Size(min = 2, message = "Last Name should have at lest 2 characters")
    private String lastName;

    @Column(length = 100, unique = true)
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    private String password;

    @Column(length = 15)
    @NotEmpty
    @Size(min = 10, max = 15, message = "Phone Number should not be less than 10 characters")
    private String phoneNumber;

    @Column
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Collection<AnswerOption> answerOptions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Collection<QuizInstance> quizInstances;

    public Collection<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(Collection<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public Collection<QuizInstance> getQuizInstances() {
        return quizInstances;
    }

    public void setQuizInstances(Collection<QuizInstance> quizInstances) {
        this.quizInstances = quizInstances;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) { this.role = role; }

    public int  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
