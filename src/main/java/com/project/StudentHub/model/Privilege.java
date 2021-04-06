package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "role_privileges")
public class Privilege {
    @Id
    @Column(name = "privilege_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private String privilegeName;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public int getId() {
        return id;
    }

    public String getName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
