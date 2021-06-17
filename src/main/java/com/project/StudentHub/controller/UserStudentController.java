package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.UserStudent;
import com.project.StudentHub.repository.UserStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class UserStudentController {
    @Autowired
    private UserStudentRepository userStudentRepository;

    @PostMapping("/users/students")
    public UserStudent addStudent(@Valid @RequestBody UserStudent userStudent){
        return userStudentRepository.save(userStudent);
    }

    @GetMapping("/users/students")
    public Iterable<UserStudent> getStudent(){
        return userStudentRepository.findAll();
    }

    @GetMapping("/users/students/{id}")
    public UserStudent findStudentById(@PathVariable Integer id) {
        Optional<UserStudent> user = Optional.ofNullable(userStudentRepository.findStudentById(id));
        return user
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found"));
    }

    @DeleteMapping("/users/students/{id}")
    public void deleteStudent(@PathVariable Integer id){
        UserStudent userDelete = userStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found"));
        userStudentRepository.delete(userDelete);
    }

    @PutMapping("/users/students/{id}")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody UserStudent userStudent, @PathVariable Integer id){
        Optional<UserStudent> userStudentOptional = Optional.ofNullable(userStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found")));
        if(!userStudentOptional.isPresent())
            return ResponseEntity.notFound().build();
        userStudent.setId(id);
        userStudentRepository.save(userStudent);
        return ResponseEntity.noContent().build();
    }
}
