package com.project.StudentHub.repository;

import com.project.StudentHub.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Quiz findQuizById(Integer id);
}
