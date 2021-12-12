package com.kadli.starmony.dao;

import com.kadli.starmony.models.Chord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ChordDAOImp implements ChordDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Chord> getChords() {
        return entityManager.createQuery("FROM Chord").getResultList();
    }

    @Override
    public String addChord(){
        Query query = entityManager.createNativeQuery("select holaMundo()");
        Object rv = query.getSingleResult();
        return (String) rv;
    }
}
