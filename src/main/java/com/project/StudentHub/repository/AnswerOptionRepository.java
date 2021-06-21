package com.project.StudentHub.repository;

import com.project.StudentHub.model.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, String> {
    AnswerOption findUserOptionById(Integer id);

    @Query(
            value = "SELECT COUNT(*) \n" +
                    "FROM answer_option \n" +
                    "INNER JOIN answer on answer.id = answer_option.answer_id \n" +
                    "INNER JOIN user_student on user_student.id = answer_option.user_student_id \n" +
                    "WHERE user_student.id = ?1 AND answer.id = ?2",
            nativeQuery = true
    )
    Integer hasAnswerOptionForUserStudentIdAndAnswerId(Integer userId, Integer answerId);
}
