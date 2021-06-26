package com.project.StudentHub.repository;

import com.project.StudentHub.dto.getClassroomCourseProperties;
import com.project.StudentHub.dto.getCourseProperties;
import com.project.StudentHub.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Classroom findClassroomById(Integer id);

    @Query(
            value = "SELECT classroom.*\n" +
                    "FROM classroom\n" +
                    "INNER JOIN user_student on user_student.classroom_id = classroom.id \n" +
                    "INNER JOIN user on user.id = user_student.user_id \n" +
                    "WHERE user.id = ?1",
            nativeQuery = true
    )
    Classroom findStudentClassroomByUserId(Integer userId);

    @Query(
            value = "SELECT course.id as courseId, classroom.id as classroomId, course.name as courseName\n" +
                    "FROM classrooms_courses\n" +
                    "INNER JOIN course ON course.id = classrooms_courses.course_id\n" +
                    "INNER JOIN classroom ON classroom.id = classrooms_courses.classroom_id\n" +
                    "WHERE classroom.id = ?1\n",
            nativeQuery = true
    )
    List<getClassroomCourseProperties> findAllClassroomCourses(Integer classroomId);
}
