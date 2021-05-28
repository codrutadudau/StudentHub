package com.project.StudentHub.repository;

import com.project.StudentHub.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Answer findAnswerById(Integer id);
}
