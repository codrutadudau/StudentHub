package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true, length = 100)
    private String firstName;

    @Column(nullable = true, length = 100)
    private String lastName;

    @Column(nullable = true, length = 100)
    private String email;

    @Column(nullable = true, length = 50)
    private String password;

    @Column(nullable = true, length = 15)
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
