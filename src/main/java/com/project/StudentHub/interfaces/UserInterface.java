package com.project.StudentHub.interfaces;

import com.project.StudentHub.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserInterface extends CrudRepository<User, Integer>{
    User findUserById(Integer id);
}
