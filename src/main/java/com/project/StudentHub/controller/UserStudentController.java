package com.project.StudentHub.controller;

import com.project.StudentHub.dto.UserStudentDto;
import com.project.StudentHub.dto.getStudentProperties;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.UserStudent;
import com.project.StudentHub.repository.ClassroomRepository;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.repository.UserStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class UserStudentController {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserStudentRepository userStudentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user_students")
    public UserStudent addStudent(@Valid @RequestBody UserStudentDto userStudent){
        UserStudent student = new UserStudent();
        student.setUser(userRepository.findUserById(userStudent.getUser()));
        student.setClassroom(classroomRepository.findClassroomById(userStudent.getClassroom()));
        student.setIdentificationNumber(userStudent.getIdentificationNumber());

        return userStudentRepository.save(student);
    }

    @GetMapping("/user_students")
    public Iterable<UserStudent> getStudent(){
        return userStudentRepository.findAll();
    }

    @GetMapping("/user_students/{id}")
    public UserStudent findStudentById(@PathVariable Integer id) {
        Optional<UserStudent> user = Optional.ofNullable(userStudentRepository.findUserStudentById(id));
        return user
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found"));
    }

    @GetMapping("/user_students/name")
    public List<getStudentProperties> getStudentsWithName(){
        return userStudentRepository.findAllStudents();
    }

    @DeleteMapping("/user_students/{id}")
    public void deleteStudent(@PathVariable Integer id){
        UserStudent userDelete = userStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found"));
        userStudentRepository.delete(userDelete);
    }

    @PutMapping("/user_students/{id}")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody UserStudentDto userStudent, @PathVariable Integer id){
        Optional<UserStudent> userStudentOptional = Optional.ofNullable(userStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found")));
        if(!userStudentOptional.isPresent())
            return ResponseEntity.notFound().build();

        UserStudent student = new UserStudent();
        student.setId(id);
        student.setUser(userRepository.findUserById(id));
        student.setClassroom(classroomRepository.findClassroomById(userStudent.getClassroom()));
        student.setIdentificationNumber(userStudent.getIdentificationNumber());

        userStudentRepository.save(student);

        return ResponseEntity.noContent().build();
    }
}
