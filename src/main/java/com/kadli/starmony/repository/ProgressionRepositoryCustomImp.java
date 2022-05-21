package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Progression;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class ProgressionRepositoryCustomImp implements ProgressionRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Progression> findByAttribute(String attribute, String value) {
        List<Progression> progression = entityManager.createQuery("" +
                        "from Progression p" +
                        " where p." + attribute + " = :value", Progression.class)
                .setParameter("value", value)
                .getResultList();
        if(progression.isEmpty()) return Optional.empty();
        return Optional.of(progression.get(0));
    }
}
