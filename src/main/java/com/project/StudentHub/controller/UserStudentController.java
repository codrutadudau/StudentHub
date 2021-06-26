package com.project.StudentHub.controller;

import com.project.StudentHub.dto.UserStudentDto;
import com.project.StudentHub.dto.getStudentProperties;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Course;
import com.project.StudentHub.model.User;
import com.project.StudentHub.model.UserStudent;
import com.project.StudentHub.repository.ClassroomRepository;
import com.project.StudentHub.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/user_students")
    public UserStudent addStudent(@Valid @RequestBody UserStudentDto userStudent){
        UserStudent student = new UserStudent();
        User user = userRepository.findUserById(userStudent.getUser());
        user.setRole(roleRepository.findByName("ROLE_STUDENT"));
        userRepository.save(user);

        student.setUser(user);
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
        return userStudentRepository.findByUserId(userRepository.findUserById(id).getId());
    }

    @GetMapping("/user_students/name")
    public List<getStudentProperties> getStudentsWithName(){
        return userStudentRepository.findAllStudents();
    }

    @DeleteMapping("/user_students/{id}")
    public void deleteStudent(@PathVariable Integer id){
        UserStudent userDelete = userStudentRepository.findById(userStudentRepository.findByUserId(id).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found"));


        userStudentRepository.delete(userDelete);
        userRepository.delete(userDelete.getUser());
    }

    @PutMapping("/user_students/{id}")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody UserStudentDto userStudent, @PathVariable Integer id){
        Optional<UserStudent> userStudentOptional = Optional.ofNullable(userStudentRepository.findById(userStudentRepository.findByUserId(userStudent.getUser()).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found")));
        if(!userStudentOptional.isPresent())
            return ResponseEntity.notFound().build();

        userStudentOptional.get().setUser(userRepository.findUserById(id));
        userStudentOptional.get().setClassroom(classroomRepository.findClassroomById(userStudent.getClassroom()));
        userStudentOptional.get().setIdentificationNumber(userStudent.getIdentificationNumber());

        userStudentRepository.save(userStudentOptional.get());

        return ResponseEntity.noContent().build();
    }
}
