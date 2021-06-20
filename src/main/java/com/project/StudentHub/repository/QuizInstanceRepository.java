package com.project.StudentHub.repository;

import com.project.StudentHub.model.Classroom;
import com.project.StudentHub.model.QuizInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizInstanceRepository extends JpaRepository<QuizInstance,Integer> {
    Optional<QuizInstance> findQuizInstanceById(Integer id);

    @Query(
            value = "SELECT quiz_instance.*\n" +
                    "FROM quiz_instance\n" +
                    "INNER JOIN user_student on quiz_instance.user_student_id = user_student.id \n" +
                    "INNER JOIN user on user.id = user_student.user_id \n" +
                    "WHERE user.id = ?1",
            nativeQuery = true
    )
    List<QuizInstance> findQuizInstancesByUserId(Integer userId);
}
