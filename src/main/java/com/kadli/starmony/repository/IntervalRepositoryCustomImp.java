package com.kadli.starmony.repository;


import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.utilities.Symbols;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class IntervalRepositoryCustomImp implements IntervalRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    // Obtener por Atributo
    @Override
    public Optional<Interval> findByAttribute(String attribute, String value) {
        List<Interval> intervals = entityManager.createQuery("" +
                        "FROM Interval i" +
                        " WHERE i." + attribute + " = :value", Interval.class)
                .setParameter( "value", value ).getResultList();
        return intervals == null || intervals.isEmpty() ? Optional.empty() : Optional.of( intervals.get(0) );
    }


    // Obtener por Semitonos
    @Override
    public Optional<Interval> getIntervalWithSemitones(Integer semitones) {
        return Optional.of(entityManager.createQuery("" +
                        "FROM Interval i" +
                        " WHERE i.semitones = :semitones", Interval.class)
                .setParameter("semitones", semitones)
                .getResultList().get(0));
    }

    @Override
    public List<Interval> getIntervalsWithSemitones(List<Integer> semitones) {
        return entityManager.createQuery("" +
                        "FROM Interval i" +
                        " WHERE i.semitones in (:semitones)", Interval.class)
                .setParameter("semitones", semitones)
                .getResultList();
    }


    // Obtener por Acorde
    @Override
    public List<Interval> getIntervalsOfChord(Long id) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT i" +
                        " FROM Interval i" +
                        " INNER JOIN i.chordIntervals ci" +
                        " INNER JOIN ci.chordOfInterval c" +
                        " WHERE c.id = :idChord", Interval.class)
                .setParameter("idChord", id)
                .getResultList();
    }

    @Override
    public List<Interval> getIntervalsOfChords(List<Long> ids) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT i" +
                        " FROM Interval i" +
                        " INNER JOIN i.chordIntervals ci" +
                        " INNER JOIN ci.chordOfInterval c" +
                        " WHERE c.id in (:idChord)", Interval.class)
                .setParameter("idChord", ids)
                .getResultList();
    }


    // Obtener por Notas
    @Override
    public Optional<Interval> getIntervalOfNotes(Long note1, Long note2) {
        return Optional.of(entityManager.createQuery("" +
                        "SELECT DISTINCT i" +
                        " FROM Interval i" +
                        " INNER JOIN i.concreteIntervals ci" +
                        " INNER JOIN ci.firstNote fn" +
                        " INNER JOIN ci.lastNote ln" +
                        " WHERE fn.id = :id1" +
                        "   and ln.id = :id2", Interval.class)
                .setParameter("id1", note1)
                .setParameter("id2", note2)
                .getResultList().get(0));
    }

    @Override
    public List<Interval> getIntervalsOfNotes(List<Long> ids) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT i" +
                        " FROM Interval i" +
                        " INNER JOIN i.concreteIntervals ci" +
                        " INNER JOIN ci.firstNote fn" +
                        " INNER JOIN ci.lastNote ln" +
                        " WHERE fn.id in (:ids)" +
                        "   and ln.id in (:ids)", Interval.class)
                .setParameter("ids", ids)
                .getResultList();
    }


    // Obtener por Escalas
    @Override
    public List<Interval> getIntervalsOfScaleCodeByTonic(String scaleCode) {
        List<Interval> intervals = new ArrayList<>();
        String codeString[] = scaleCode.split(Symbols.SYMBOL_SEPARATION_SCALE);
        int code = 0;
        for(String it:codeString){
            code += Integer.parseInt( it );
            intervals.add( this.getIntervalWithSemitones( code ).get() );
        }
        return intervals;
    }

    @Override
    public List<Interval> getIntervalsOfScaleCodeByAll(String scaleCode) {
        return this.getIntervalsWithSemitones( Arrays.stream( scaleCode.split(Symbols.SYMBOL_SEPARATION_SCALE) ).map(codeString -> Integer.parseInt(codeString) ).distinct().collect(Collectors.toList()) );
    }

    @Override
    public List<Interval> getIntervalsById(List<Long> intervalsId) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT i" +
                        " FROM Interval i" +
                        " WHERE i.id in (:ids)", Interval.class)
                .setParameter("ids", intervalsId)
                .getResultList();
    }

}