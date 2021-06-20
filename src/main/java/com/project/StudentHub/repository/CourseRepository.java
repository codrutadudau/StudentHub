package com.project.StudentHub.repository;

import com.project.StudentHub.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findCourseById(Integer id);

    @Query(
            value = "SELECT course.*\n" +
                    "FROM course\n" +
                    "INNER JOIN user_teacher ON course.teacher_id = user_teacher.id \n" +
                    "INNER JOIN user on user.id = user_teacher.user_id \n" +
                    "WHERE user.id = ?1",
            nativeQuery = true
    )
    List<Course> findCoursesForTeacherWithUserId(Integer userId);
}
