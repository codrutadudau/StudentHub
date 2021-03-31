package com.project.StudentHub.controller;

import com.project.StudentHub.interfaces.UserInterface;
import com.project.StudentHub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return userInterface.save(user);
    }

    @GetMapping("/users")
    public Iterable<User> getUser(){
        return userInterface.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Integer id){
        return userInterface.findUserById(id);
    }
}
