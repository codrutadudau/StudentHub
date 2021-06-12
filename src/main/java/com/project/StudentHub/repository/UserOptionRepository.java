package com.project.StudentHub.repository;

import com.project.StudentHub.model.UserOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionRepository extends JpaRepository<UserOption, String> {
    UserOption findUserOptionById(Integer id);
}
