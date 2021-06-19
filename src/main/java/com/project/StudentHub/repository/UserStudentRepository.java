package com.project.StudentHub.repository;

import com.project.StudentHub.model.UserStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserStudentRepository extends JpaRepository<UserStudent, Integer>{
    UserStudent findUserStudentById(Integer id);

    @Query("SELECT COUNT(qi) FROM QuizInstance qi WHERE qi.finishedAt IS NULL AND qi.userStudent = ?1")
    Integer hasInProgressQuizzes(UserStudent userStudent);
}
