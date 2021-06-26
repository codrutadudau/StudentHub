package com.project.StudentHub.repository;

import com.project.StudentHub.dto.getStudentProperties;
import com.project.StudentHub.model.UserStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStudentRepository extends JpaRepository<UserStudent, Integer>{
    UserStudent findUserStudentById(Integer id);
    UserStudent findByUserId(Integer id);

    @Query("SELECT COUNT(qi) FROM QuizInstance qi WHERE qi.finishedAt IS NULL AND qi.userStudent = ?1")
    Integer hasInProgressQuizzes(UserStudent userStudent);

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
