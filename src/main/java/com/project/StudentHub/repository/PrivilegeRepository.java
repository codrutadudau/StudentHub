package com.project.StudentHub.repository;

import com.project.StudentHub.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {
    Privilege findByName(String name);
}
