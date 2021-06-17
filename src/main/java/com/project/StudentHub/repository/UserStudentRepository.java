package com.project.StudentHub.repository;

import com.project.StudentHub.model.UserStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStudentRepository extends JpaRepository<UserStudent, Integer>{
    UserStudent findUserStudentById(Integer id);
}
