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
    public Optional<Progression> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Progression> findBySymbol(String symbol) {
        return Optional.empty();
    }
}
