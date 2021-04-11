package com.project.StudentHub.model;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "user")

public class User {

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

    @Column(length = 50)
    @NotEmpty
    @Size(min = 8, max = 15, message = "Password should be between 8 and 15 characters")
    private String password;

    @Column(length = 15)
    @NotEmpty
    @Size(min = 10, max = 15, message = "Phone Number should not be less than 10 characters")
    private String phoneNumber;

    @Column
    private boolean enabled;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) { this.roles = roles; }

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
