package com.project.StudentHub.repository;

import com.project.StudentHub.model.QuizInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizInstanceRepository extends JpaRepository<QuizInstance,Integer> {
    Optional<QuizInstance> findQuizInstanceById(Integer id);
}
