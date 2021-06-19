package com.project.StudentHub.repository;

import com.project.StudentHub.model.Quiz;
import com.project.StudentHub.model.UserTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Quiz findQuizById(Integer id);
    List<Quiz> findByCourseId(Integer id);

    @Query(
            value = "SELECT quiz.*\n" +
                    "FROM quiz\n" +
                    "INNER JOIN course ON course.id = quiz.course_id\n" +
                    "INNER JOIN user_teacher ON course.teacher_id = user_teacher.id \n" +
                    "WHERE user_teacher.id = ?1",
            nativeQuery = true
    )
    List<Quiz> findQuizzesByUserTeacher(UserTeacher userTeacher);
}
