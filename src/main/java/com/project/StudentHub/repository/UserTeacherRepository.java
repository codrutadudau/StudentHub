package com.project.StudentHub.repository;

import com.project.StudentHub.model.UserTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeacherRepository extends JpaRepository<UserTeacher, Integer> {
    UserTeacher findTeacherById(Integer id);
}
