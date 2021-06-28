package com.project.StudentHub.repository;

import com.project.StudentHub.dto.getQuizInstanceProperties;
import com.project.StudentHub.dto.getStudentProperties;
import com.project.StudentHub.model.UserStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStudentRepository extends JpaRepository<UserStudent, Integer>{
    UserStudent findUserStudentById(Integer id);
    UserStudent findByUserId(Integer id);

    @Query(
            value = "SELECT COUNT(qi.id) " +
            "FROM quiz_instance qi " +
            "INNER JOIN quiz q on q.id = qi.quiz_id " +
            "INNER JOIN course c on c.id = q.course_id " +
            "INNER JOIN classrooms_courses cc on  c.id = cc.course_id " +
            "WHERE qi.finished_at IS NULL AND qi.user_student_id = ?1 AND cc.classroom_id = ?2",
            nativeQuery = true
    )
    Integer hasInProgressQuizzes(Integer studentId, Integer classroomId);

    @Query(
            value = "SELECT qi.id as id " +
                    "FROM quiz_instance qi " +
                    "INNER JOIN quiz q on q.id = qi.quiz_id " +
                    "INNER JOIN course c on c.id = q.course_id " +
                    "INNER JOIN classrooms_courses cc on  c.id = cc.course_id " +
                    "WHERE qi.started_at IS NOT NULL AND qi.finished_at IS NOT NULL AND qi.user_student_id = ?1 AND cc.classroom_id = ?2 " +
                    "LIMIT 1",
            nativeQuery = true
    )
    getQuizInstanceProperties getInProgressQuizInstanceForStudentAndClassroom(Integer studentId, Integer classroomId);

    @Query(
            value = "SELECT user.id, user.first_name as firstName, user.last_name as lastName, user_student.id as studentId, user_student.identification_number as identificationNumber, classroom.name as classroomName, classroom.id as classroomId\n" +
                    "FROM user\n" +
                    "INNER JOIN user_student ON user.id = user_student.user_id\n" +
                    "INNER JOIN classroom ON classroom.id = user_student.classroom_id\n",
            nativeQuery = true
    )
    List<getStudentProperties> findAllStudents();

    @Query(
            value = "SELECT user.id, user.first_name as firstName, user.last_name as lastName, user_student.id as studentId, user_student.identification_number as identificationNumber, classroom.name as classroomName, classroom.id as classroomId\n" +
                    "FROM user\n" +
                    "INNER JOIN user_student ON user.id = user_student.user_id\n" +
                    "INNER JOIN classroom ON classroom.id = user_student.classroom_id\n" +
                    "WHERE user_student.id = ?1",
            nativeQuery = true
    )
    getStudentProperties findStudentById(Integer id);
}
