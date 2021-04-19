package com.project.StudentHub.repository;

import com.project.StudentHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);
    User findByEmail(String email);
}
