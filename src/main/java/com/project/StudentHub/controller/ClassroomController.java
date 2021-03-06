package com.project.StudentHub.controller;

import com.google.common.collect.Lists;
import com.project.StudentHub.dto.ClassroomDto;
import com.project.StudentHub.dto.getClassroomCourseProperties;
import com.project.StudentHub.dto.getQuizInstanceProperties;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.*;
import com.project.StudentHub.repository.ClassroomRepository;
import com.project.StudentHub.repository.CourseRepository;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.repository.UserStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class ClassroomController {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStudentRepository userStudentRepository;

    @PostMapping("/classrooms")
    public Classroom addClassroom(@Valid @RequestBody ClassroomDto classroom){
        Classroom classroom1 = new Classroom();
        classroom1.setName(classroom.getName());
        classroom1.setYear(classroom.getYear());

        return classroomRepository.save(classroom1);
    }

    @GetMapping("/classrooms")
    public List<Classroom> getClassroom(@RequestParam("user") Optional<Integer> userId){
        if (userId.isPresent()) {
            return Lists.newArrayList(classroomRepository.findStudentClassroomByUserId(userId.get()));
        }

        return classroomRepository.findAll();
    }

    @GetMapping("/classrooms/{id}")
    public Classroom findClassroomById(@PathVariable Integer id) {
        Optional<Classroom> classroom = Optional.ofNullable(classroomRepository.findClassroomById(id));

        return classroom
                .orElseThrow(() -> new ResourceNotFoundException("Classroom with id: " + id + " not found"));
    }

    @GetMapping("/classrooms/{id}/courses")
    public List<getClassroomCourseProperties> findClassroomCourses(@PathVariable Integer id) {
        Optional<Classroom> classroom = Optional.ofNullable(classroomRepository.findClassroomById(id));
        if(!classroom.isPresent()) {
            throw new ResourceNotFoundException("Classroom with id: " + id + " not found");
        }

        return classroomRepository.findAllClassroomCourses(id);
    }

    @GetMapping("/classrooms/{id}/students")
    public ArrayList<Object> getClassroomStudents(@PathVariable Integer id){
        Optional<Classroom> classroom = Optional.ofNullable(classroomRepository.findClassroomById(id));
        if(!classroom.isPresent()) {
            throw new ResourceNotFoundException("Classroom with id: " + id + " not found");
        }

        Collection<UserStudent> userStudents = classroom.get().getUserStudents();

        ArrayList<Object> students = new ArrayList<>();
        for (UserStudent s : userStudents) {
            HashMap<Object, Object> student = new HashMap<>();
            User userStudent = userRepository.findUserById(s.getUser().getId());
            student.put("studentId", s.getId());
            student.put("firstName", userStudent.getFirstName());
            student.put("lastName", userStudent.getLastName());
            student.put("email", userStudent.getEmail());
            student.put("hasInProgressQuizzes", userStudentRepository.hasInProgressQuizzes(s.getId(), classroom.get().getId()));
            getQuizInstanceProperties qi =  userStudentRepository.getInProgressQuizInstanceForStudentAndClassroom(s.getId(), classroom.get().getId());

            Integer finishedQi = 0;
            if (null != qi) {
                finishedQi = qi.getId();
            }

            student.put("finishedQuizInstance", finishedQi);
            students.add(student);
        }

        return students;
    }

    @DeleteMapping("/classrooms/{id}")
    public void deleteClassroom(@PathVariable Integer id){
        Optional<Classroom> classroom = Optional.ofNullable(Optional.ofNullable(classroomRepository.findClassroomById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));

        classroomRepository.delete(classroomRepository.findClassroomById(id));
    }

    @PostMapping("/classrooms/{id}/course/{courseId}")
    public void addClassroomCourse(@PathVariable Integer id, @PathVariable Integer courseId){
        Optional<Classroom> classroom = Optional.ofNullable(Optional.ofNullable(classroomRepository.findClassroomById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));

        Course course = courseRepository.findCourseById(courseId);
        classroom.get().addCourse(course);
        classroomRepository.save(classroom.get());
    }

    @DeleteMapping("/classrooms/{id}/course/{courseId}")
    public void deleteClassroomCourse(@PathVariable Integer id, @PathVariable Integer courseId){
        Optional<Classroom> classroom = Optional.ofNullable(Optional.ofNullable(classroomRepository.findClassroomById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));

        Course course = courseRepository.findCourseById(courseId);
        classroom.get().removeCourse(course);
        classroomRepository.save(classroom.get());
    }

    @PutMapping("/classrooms/{id}")
    public ResponseEntity<Object> updateClassroom(@PathVariable Integer id, @Valid @RequestBody ClassroomDto classroom){
        Optional<Classroom> classroomOptional = Optional.ofNullable(Optional.ofNullable(classroomRepository.findClassroomById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));
        if(!classroomOptional.isPresent())
            return ResponseEntity.notFound().build();

        Classroom classroom1 = new Classroom();
        classroom1.setId(id);
        classroom1.setName(classroom.getName());
        classroom1.setYear(classroom.getYear());
        classroomRepository.save(classroom1);

        return ResponseEntity.noContent().build();
    }
}
