package com.project.StudentHub.repository;

import com.project.StudentHub.model.Answer;
import com.project.StudentHub.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Answer findAnswerById(Integer id);
}
