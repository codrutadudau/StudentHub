package com.project.StudentHub.controller;

import com.project.StudentHub.dto.CourseDto;
import com.project.StudentHub.dto.getAverageGradeByClassroomProperties;
import com.project.StudentHub.dto.getCourseProperties;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Course;
import com.project.StudentHub.model.Quiz;
import com.project.StudentHub.repository.CourseRepository;
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
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserTeacherRepository userTeacherRepository;

    @PostMapping("/courses")
    public Course addCourse(@Valid @RequestBody CourseDto course){
        Course course1 = new Course();
        course1.setName(course.getName());
        course1.setUserTeacher(userTeacherRepository.findTeacherById(course.getUserTeacher()));

        return courseRepository.save(course1);
    }

    @GetMapping("/courses")
    public Iterable<Course> getCourses(@RequestParam("user") Optional<Integer> userId) {
        if (userId.isPresent()) {
            return courseRepository.findCoursesForTeacherWithUserId(userId.get());
        }

        return courseRepository.findAll();
    }

    @GetMapping("/courses/{id}")
    public Course findCourseById(@PathVariable Integer id) {
        Optional<Course> course = Optional.ofNullable(courseRepository.findCourseById(id));

        return course
                .orElseThrow(() -> new ResourceNotFoundException("Course with id: " + id + " not found"));
    }

    @GetMapping("/courses/averageGradePerClassroom")
    public List<getAverageGradeByClassroomProperties> getAverageGradePerClassroom() {
        return courseRepository.getAverageGradePerClassroom();
    }

    @GetMapping("/courses/teacher")
    public List<getCourseProperties> getCoursesWithTeacherName() {
        return courseRepository.findAllCoursesWithTeacherName();
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Integer id){
        Optional<Course> course = Optional.ofNullable(Optional.ofNullable(courseRepository.findCourseById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Course with id: " + id + " not found")));

        courseRepository.delete(courseRepository.findCourseById(id));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseDto course){
        Optional<Course> courseOptional = Optional.ofNullable(Optional.ofNullable(courseRepository.findCourseById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Course with id: " + id + " not found")));
        if(!courseOptional.isPresent())
            return ResponseEntity.notFound().build();

        courseOptional.get().setId(id);
        courseOptional.get().setName(course.getName());
        courseRepository.save(courseOptional.get());

        return ResponseEntity.noContent().build();
    }
}
