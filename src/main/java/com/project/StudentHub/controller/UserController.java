package com.project.StudentHub.controller;

import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.model.User;
import com.project.StudentHub.model.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userInterface;

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

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){ userInterface.deleteById(id);}

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Integer id){
        Optional<User> userOptional = userInterface.findById(id);
        if(!userOptional.isPresent())
            return ResponseEntity.notFound().build();
        user.setId(id);
        userInterface.save(user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Object> partialUserUpdate(@RequestBody UserUpdate userUpdate, @PathVariable Integer id){
        Optional<User> userOptional = userInterface.findById(id);
        if(!userOptional.isPresent())
            return ResponseEntity.notFound().build();

        User user = userOptional.get();
        if(userUpdate.getFirstName() != null){
            user.setFirstName((userUpdate.getFirstName()));
        }
        if(userUpdate.getLastName() != null){
            user.setLastName((userUpdate.getLastName()));
        }
        if(userUpdate.getEmail() != null){
            user.setEmail((userUpdate.getEmail()));
        }
        if(userUpdate.getPassword() != null){
            user.setPassword((userUpdate.getPassword()));
        }
        if(userUpdate.getPhoneNumber() != null){
            user.setPhoneNumber((userUpdate.getPhoneNumber()));
        }
        userInterface.save(user);
        return ResponseEntity.noContent().build();
    }
}
