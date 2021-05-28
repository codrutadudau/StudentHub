package com.project.StudentHub.repository;

import com.project.StudentHub.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Question findQuestionById(Integer id);
}
