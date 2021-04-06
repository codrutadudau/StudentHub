package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "users_roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true, length = 10)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(targetEntity = Privilege.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Collection<Privilege> privileges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRole_name() {
        return roleName;
    }
}
