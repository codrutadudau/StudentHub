package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true, length = 100)
    @NotEmpty
    private String firstName;

    @Column(nullable = true, length = 100)
    @NotEmpty
    private String lastName;

    @Column(nullable = true, length = 100)
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = true, length = 50)
    @NotEmpty
    @Size(min = 8, max = 15, message = "Password should be between 8 and 15 characters")
    private String password;

    @Column(nullable = true, length = 15)
    @NotEmpty
    @Size(min = 10, max = 15, message = "Phone Number should not be less than 10 characters")
    private String phoneNumber;

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setEmail(String email) { this.email = email; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
