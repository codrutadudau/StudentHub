package com.project.StudentHub.repository;

import com.project.StudentHub.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Classroom findClassroomById(Integer id);

    @Query(
            value = "SELECT classroom.*\n" +
                    "FROM classroom\n" +
                    "INNER JOIN user_student on user_student.classroom_id = classroom.id \n" +
                    "INNER JOIN user on user.id = user_student.user_id \n" +
                    "WHERE user.id = ?1",
            nativeQuery = true
    )
    Classroom findStudentClassroomByUserId(Integer userId);
}
