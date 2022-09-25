package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class ScaleRepositoryCustomImp implements ScaleRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Scale> getScalesWithIntervals(List<Interval> intervals) {
        List<Long> ids = new ArrayList<>();
        for(Interval it: intervals)
            ids.add(it.getId());
        return getScalesWithIntervalsId(ids);
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
                " where i.id=:idp", Scale.class)
                .setParameter("idp",id)
                .getResultList();
    }

    @Override
    public List<Scale> getScalesWithIntervalsId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT s" +
                        " FROM ScaleGrade sg" +
                        " INNER JOIN sg.scaleOfChord s" +
                        " INNER JOIN sg.chordOfScale c" +
                        " INNER JOIN c.chordIntervals ci" +
                        " INNER JOIN ci.intervalOfChord i" +
                        " WHERE i.id in (:ids)", Scale.class)
                .setParameter("ids",ids)
                .getResultList();
    }

    @Override
    public List<Scale> getScalesWithChordId(Long id) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT s" +
                        " FROM ScaleGrade sg" +
                        " INNER JOIN sg.scaleOfChord s" +
                        " INNER JOIN sg.chordOfScale c" +
                        " WHERE c.id = :idp", Scale.class)
                .setParameter("idp",id)
                .getResultList();
    }

    @Override
    public List<Scale> getScalesWithChordsId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT s" +
                        " FROM Scale s" +
                        " INNER JOIN s.scaleGrades sg" +
                        " INNER JOIN sg.chordOfScale c" +
                        " WHERE c.id IN (:ids)", Scale.class)
                .setParameter("ids",ids)
                .getResultList();
    }

    @Override
    public Optional<Scale> getScaleWithCode(String code) {
        return Optional.of(entityManager.createQuery("" +
                        "from Scale s" +
                        " where s.code=:code", Scale.class)
                .setParameter("code",code)
                .getResultList().get(0));
    }

    @Override
    public Long getNextId() {
        return entityManager.createQuery("" +
                        "SELECT MAX(s.id) FROM Scale s", Long.class)
                .getResultList().get(0);
    }

    @Override
    public List<Scale> getAllByMaxLength(int max) {
        int longCode = max + (max-1);
        return entityManager.createQuery( "" +
                "FROM Scale s" +
                " WHERE LENGTH(s.code) >= :longCode" ,Scale.class)
                .setParameter("longCode", longCode)
                .getResultList();
    }

    @Override
    public Optional<Scale> findByAttribute(String attribute, String value) {
        List<Scale> scales = entityManager.createQuery("" +
                        "FROM Scale s" +
                        " WHERE s." + attribute + " = :value", Scale.class)
                .setParameter( "value", value).getResultList();
        return scales == null || scales.isEmpty() ? Optional.empty() : Optional.of( scales.get(0) );
    }
}
