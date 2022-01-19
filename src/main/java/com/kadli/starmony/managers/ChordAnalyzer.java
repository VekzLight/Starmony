package com.kadli.starmony.managers;

import com.kadli.starmony.interfaces.Analyzer;
import com.kadli.starmony.interfaces.MusicalElement;
import com.kadli.starmony.models.Interval;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
@Transactional
public class ChordAnalyzer implements Analyzer {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Interval> analyzeIntervals(int id) {

        return entityManager.createQuery("FROM chord WHERE id="+ id).getResultList();
    }


    @Override
    public List<MusicalElement> analyze(MusicalElement element) {
        return null;
    }

    @Override
    public List<MusicalElement> analyze(Integer elements) {
        return null;
    }
}
