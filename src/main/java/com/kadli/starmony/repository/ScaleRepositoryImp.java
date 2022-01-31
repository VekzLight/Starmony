package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ScaleRepositoryImp implements ScaleRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Scale> getScalesWithInterval(Interval interval) {
        return getScalesWithIntervalId(interval.getId());
    }

    @Override
    public List<Scale> getScalesWithIntervals(List<Interval> intervals) {
        List<Long> ids = new ArrayList<>();
        for(Interval it: intervals)
            ids.add(it.getId());
        return getScalesWithIntervalsId(ids);
    }

    @Override
    public List<Scale> getScalesWithChord(Chord chord) {
        return getScalesWithChordId(chord.getId());
    }

    @Override
    public List<Scale> getScalesWithChords(List<Chord> chords) {
        List<Long> ids = new ArrayList<>();
        for(Chord it: chords)
            ids.add(it.getId());
        return getScalesWithChordsId(ids);
    }

    @Override
    public List<Scale> getScalesWithIntervalId(Long id) {
        return entityManager.createQuery("" +
                "select distinct s" +
                " from Scale_Grades sg" +
                " inner join sg.scale s" +
                " inner join sg.chord c" +
                " inner join c.intervals i" +
                " where i.id=:idp")
                .setParameter("idp",id)
                .getResultList();
    }

    @Override
    public List<Scale> getScalesWithIntervalsId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "select distinct s" +
                        " from Scale_Grades sg" +
                        " inner join sg.scale s" +
                        " inner join sg.chord c" +
                        " inner join c.intervals i" +
                        " where i.id in (:ids)")
                .setParameter("ids",ids)
                .getResultList();
    }

    @Override
    public List<Scale> getScalesWithChordId(Long id) {
        return entityManager.createQuery("" +
                        "select distinct s" +
                        " from Scale_Grades sg" +
                        " inner join sg.scale s" +
                        " where sg.chord.id=:idp")
                .setParameter("idp",id)
                .getResultList();
    }

    @Override
    public List<Scale> getScalesWithChordsId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "select distinct s" +
                        " from Scale_Grades sg" +
                        " inner join sg.scale s" +
                        " where sg.chord.id in (:ids)")
                .setParameter("ids",ids)
                .getResultList();
    }

    @Override
    public Scale getScaleWithCode(String code) {
        return (Scale) entityManager.createQuery("" +
                "from Scale s" +
                " where s.code=:codep")
                .setParameter("codep",code)
                .getResultList().get(0);
    }
}
