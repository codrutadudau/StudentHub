package com.project.StudentHub.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(){}

    public Privilege(String name){
        this.name = name;
    }

    public void setRoles(Collection<Role> roles) { this.roles = roles; }

    public Collection<Role> getRoles() { return roles; }

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
}
