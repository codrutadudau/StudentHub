package com.project.StudentHub.repository;

import com.project.StudentHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);
    User findUserByEmail(String email);
}
