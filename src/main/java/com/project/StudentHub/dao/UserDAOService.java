package com.project.StudentHub.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDAOService {
    private EntityManager entityManager;


}
