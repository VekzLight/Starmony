package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class ChordRepositoryCustomImp implements ChordRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Chord> findByAttribute(String attribute, String value) {
        List<Chord> chords = entityManager.createQuery("" +
                        "from Chord c" +
                        " where c.:attribute=:value")
                .setParameter("value", value)
                .setParameter("attribute", attribute)
                .getResultList();
        if(chords.isEmpty()) return Optional.empty();
        return Optional.of(chords.get(0));
    }

    @Override
    public List<Chord> getChordsWithInterval(Interval interval) {
        return getChordsWithIntervalId(interval.getId());
    }

    @Override
    public List<Chord> getChordsWithIntervals(List<Interval> intervals) {
        List<Long> ids = new ArrayList<>();
        for(Interval it: intervals)
            ids.add(it.getId());
        return getChordsWithIntervalsId(ids);
    }

    @Override
    public List<Chord> getChordsOfScale(Scale scale) {
        return getChordsOfScaleId(scale.getId());
    }

    @Override
    public List<Chord> getChordsOfScales(List<Scale> scales) {
        List<Long> ids = new ArrayList<>();
        for(Scale it: scales)
            ids.add(it.getId());
        return getChordsOfScalesId(ids);
    }

    @Override
    public List<Chord> getConcreteChords() {
        return entityManager.createQuery("" +
                        "from Chord c" +
                        " inner join c.chordIntervals")
                .getResultList();
    }

    @Override
    public List<Chord> getChordsWithIntervalId(Long id) {
        return entityManager.createQuery("" +
                        "from Chord c" +
                        " inner join c.chordIntervals i" +
                        " where i.id=:idp")
                .setParameter("idp", id)
                .getResultList();
    }

    // TODO: Crear JPQL para recuperar los intervalos del acorde
    @Override
    public List<Chord> getChordsWithIntervalsId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "select distinct c" +
                        " from Chord c" +
                        " inner join c.chordIntervals i" +
                        " where i.id in (:ids)")
                .setParameter("ids",ids)
                .getResultList();
    }

    @Override
    public List<Chord> getChordsOfScaleId(Long id) {
        return entityManager.createQuery("" +
                        "select distinct c" +
                        " from Scale_Grades sg" +
                        " inner join sg.chord c" +
                        " where sg.scale.id=:idp")
                .setParameter("idp", id)
                .getResultList();
    }

    @Override
    public List<Chord> getChordsOfScalesId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "select distinct c" +
                        " from Scale_Grades sg" +
                        " inner join sg.chord c" +
                        " where sg.scale.id in (:ids)")
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public List<Chord> getChordWithNotes(List<Note> notes) {
        return null;
    }

    @Override
    public List<Chord>  getChordWithIntervals(List<Interval> intervals) {
        List<Long> ids = new ArrayList<>();
        for(Interval it: intervals)
            ids.add(it.getId());
       return getChordWithIntervalsId(ids);
    }

    @Override
    public List<Chord> getChordWithIntervalsId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "from Chord c" +
                        " inner join c.chordIntervals i" +
                        " where i.cc_id.id_interval in (:idp)")
                .setParameter("idp", ids)
                .getResultList();
    }

}
