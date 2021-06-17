package com.project.StudentHub.repository;

import com.project.StudentHub.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findCourseById(Integer id);
}
