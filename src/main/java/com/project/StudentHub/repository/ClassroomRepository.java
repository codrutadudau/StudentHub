package com.project.StudentHub.repository;

import com.project.StudentHub.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Classroom findClassroomById(Integer id);
}
