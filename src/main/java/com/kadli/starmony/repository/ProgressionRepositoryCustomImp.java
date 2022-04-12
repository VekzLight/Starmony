package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Progression;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class ProgressionRepositoryCustomImp implements ProgressionRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Progression> findByAttribute(String attribute, String value) {
        return Optional.empty();
    }
}
