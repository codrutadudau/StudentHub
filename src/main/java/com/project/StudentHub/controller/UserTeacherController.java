package com.project.StudentHub.controller;

import com.google.common.collect.Lists;
import com.project.StudentHub.dto.getTeacherProperties;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Classroom;
import com.project.StudentHub.model.Course;
import com.project.StudentHub.model.UserTeacher;
import com.project.StudentHub.repository.UserTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class UserTeacherController {
    @Autowired
    private UserTeacherRepository userTeacherRepository;

    @PostMapping("/user_teachers")
    public UserTeacher addTeacher(@Valid @RequestBody UserTeacher userTeacher){
        return userTeacherRepository.save(userTeacher);
    }

    @GetMapping("/user_teachers")
    public List<UserTeacher> getTeachers(@RequestParam("user") Optional<Integer> userId){
        if (userId.isPresent()) {
            return Lists.newArrayList(userTeacherRepository.findTeacherByUserId(userId.get()));
        }

        return userTeacherRepository.findAll();
    }

    @GetMapping("/user_teachers/name")
    public List<getTeacherProperties> getTeachersWithName(){
        return userTeacherRepository.findAllTeachers();
    }

    @GetMapping("/user_teachers/{id}/courses")
    public ArrayList<Object> getTeacherCourses(@PathVariable Integer id){
        Optional<UserTeacher> userTeacher = Optional.ofNullable(userTeacherRepository.findTeacherById(id));
        if(!userTeacher.isPresent()) {
            throw new ResourceNotFoundException("Teacher with id: " + id + " not found");
        }

        ArrayList<Object> response = new ArrayList<>();
        Collection<Course> courses = userTeacher.get().getCourses();

        for (Course c : courses) {
            HashMap<Object, Object> courseClassrooms = new HashMap<Object, Object>();
            Collection<Classroom> classrooms = c.getClassrooms();
            courseClassrooms.put("course", c);
            courseClassrooms.put("classrooms", classrooms);
            response.add(courseClassrooms);
        }

        return response;
    }

    @GetMapping("/user_teachers/{id}")
    public UserTeacher findTeacherById(@PathVariable Integer id) {
        Optional<UserTeacher> user = Optional.ofNullable(userTeacherRepository.findTeacherById(id));
        return user
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id: " + id + " not found"));
    }

    @DeleteMapping("/user_teachers/{id}")
    public void deleteTeacher(@PathVariable Integer id){
        UserTeacher userDelete = userTeacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id: " + id + " not found"));
        userTeacherRepository.delete(userDelete);
    }

    @PutMapping("/user_teachers/{id}")
    public ResponseEntity<Object> updateTeacher(@Valid @RequestBody UserTeacher userTeacher, @PathVariable Integer id){
        Optional<UserTeacher> userTeacherOptional = Optional.ofNullable(userTeacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found")));
        if(!userTeacherOptional.isPresent())
            return ResponseEntity.notFound().build();

        userTeacher.setId(id);
        userTeacherRepository.save(userTeacher);

        return ResponseEntity.noContent().build();
    }
}
