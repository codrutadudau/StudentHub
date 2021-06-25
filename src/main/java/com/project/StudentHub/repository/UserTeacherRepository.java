package com.project.StudentHub.repository;

import com.project.StudentHub.dto.getTeacherProperties;
import com.project.StudentHub.model.UserTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTeacherRepository extends JpaRepository<UserTeacher, Integer> {
    UserTeacher findTeacherById(Integer id);
    UserTeacher findTeacherByUserId(Integer id);

    @Query(
            value = "SELECT user_teacher.id as id, CONCAT(user.first_name, ' ', user.last_name) as name\n" +
                    "FROM user_teacher\n" +
                    "INNER JOIN user ON user.id = user_teacher.user_id\n",
            nativeQuery = true
    )
    List<getTeacherProperties> findAllTeachers();
}
