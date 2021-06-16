package com.project.StudentHub.repository;

import com.project.StudentHub.model.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, String> {
    AnswerOption findUserOptionById(Integer id);
}
