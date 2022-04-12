package com.kadli.starmony.repository;


import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;

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
                .setParameter( "value", value).getResultList();
        return intervals == null ? Optional.empty() : Optional.of( intervals.get(0) );
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
                        " INNER JOIN i.chordIntervals c" +
                        " WHERE c.id.id_chord = :idChord", Interval.class)
                .setParameter("idChord", id)
                .getResultList();
    }

    @Override
    public List<Interval> getIntervalsOfChords(List<Long> ids) {
        return entityManager.createQuery("" +
                        "SELECT DISTINCT i" +
                        " FROM Interval i" +
                        " INNER JOIN i.chordIntervals c" +
                        " WHERE c.id.id_chord in (:idChord)", Interval.class)
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
                        " WHERE ci.id.id_note1 = :id1" +
                        "   and ci.id.id_note2 = :id2", Interval.class)
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
                        " WHERE ci.id.id_note1 in (:ids)" +
                        "   and ci.id.id_note2 in (:ids)", Interval.class)
                .setParameter("ids", ids)
                .getResultList();
    }


    // Obtener por Escalas
    @Override
    public List<Interval> getIntervalsOfScaleCodeByTonic(String scaleCode) {
        List<Interval> intervals = new ArrayList<>();
        String codeString[] = scaleCode.split("–");
        int code = 0;
        for(String it:codeString){
            code += Integer.parseInt( it );
            intervals.add( this.getIntervalWithSemitones( code ).get() );
        }
        return intervals;
    }

    @Override
    public List<Interval> getIntervalsOfScaleCodeByAll(String scaleCode) {
        return this.getIntervalsWithSemitones( Arrays.stream( scaleCode.split("–") ).map(codeString -> Integer.parseInt(codeString) ).distinct().collect(Collectors.toList()) );
    }

}