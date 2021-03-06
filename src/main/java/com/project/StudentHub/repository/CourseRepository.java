package com.project.StudentHub.repository;

import com.project.StudentHub.dto.getAverageGradeByClassroomProperties;
import com.project.StudentHub.dto.getCourseProperties;
import com.project.StudentHub.dto.getStudentProperties;
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

    @Query(
            value = "SELECT course.id as id, course.name as name, CONCAT(user.first_name, ' ', user.last_name) as teacherName\n" +
                    "FROM course\n" +
                    "INNER JOIN user_teacher ON course.teacher_id = user_teacher.id\n" +
                    "INNER JOIN user ON user.id = user_teacher.user_id\n",
            nativeQuery = true
    )
    List<getCourseProperties> findAllCoursesWithTeacherName();

    @Query(
            value = "SELECT c.teacher_id as teacher, c.name as course, cl.name classroom, AVG(NULLIF(qi.grade, 0)) AS average, SUM(CASE WHEN qi.grade > 0 THEN 1 ELSE 0 END) as quizzesTaken\n" +
                    "FROM quiz_instance qi\n" +
                    "INNER JOIN quiz q on q.id = qi.quiz_id\n" +
                    "INNER JOIN course c on c.id = q.course_id\n" +
                    "INNER JOIN user_student us ON us.id = qi.user_student_id\n" +
                    "INNER JOIN classroom cl ON cl.id = us.classroom_id\n" +
                    "GROUP BY us.classroom_id\n",
            nativeQuery = true
    )
    List<getAverageGradeByClassroomProperties> getAverageGradePerClassroom();
}
