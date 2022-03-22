package com.kadli.starmony.repository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class ProgressionRepositoryCustomImp implements ProgressionRepositoryCustom{

    @Autowired
    private EntityManager entityManager;

}
