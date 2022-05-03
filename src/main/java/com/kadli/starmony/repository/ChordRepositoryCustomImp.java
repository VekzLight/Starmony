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
import java.util.stream.Collectors;

@Transactional
public class ChordRepositoryCustomImp implements ChordRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Chord> findByAttribute(String attribute, String value) {
        List<Chord> chords = entityManager.createQuery("" +
                        "from Chord c" +
                        " where c." + attribute + " = :value", Chord.class)
                .setParameter("value", value)
                .getResultList();
        if(chords.isEmpty()) return Optional.empty();
        return Optional.of(chords.get(0));
    }

    @Override
    public List<Chord> getChordsWithIntervals(List<Interval> intervals) {
        List<Long> ids = new ArrayList<>();
        List<String> expressions = new ArrayList<>();
        String expression = "1";

        for(Interval it: intervals){
            ids.add(it.getId());
            String symbol = it.getSymbol();
            if(symbol.contains("/")) {
                expressions.add(symbol);
                expression += "%__%";
            }
            else {
                    expression += "%-"+it.getSymbol()+"-";
            }
        }
        if(expression.endsWith("-") )
            expression = expression.substring(0, expression.length()-1);

        expression = expression.replace("-%-","-%");

        System.out.println(expression);
        return getChordsWithIntervalsId(ids, expression, expressions);
    }

    @Override
    public List<Chord> getChordsOfScale(Scale scale) {
        return getChordsOfScaleId(scale.getId());
    }


    @Override
    public List<Chord> getChordsWithIntervalsId(List<Long> ids, String expressionBase, List<String> expressions) {
        String query = "SELECT DISTINCT c" +
                " FROM Chord c" +
                " INNER JOIN c.chordIntervals ci" +
                " WHERE ci.id.id_interval IN (:idp)" +
                " AND c.code LIKE ((:expressionBase)) ";

        if(!expressions.isEmpty()) {
            query += "AND ";
            for(String it: expressions){
                String subSymbols[] = it.split("/");
                query += "(c.code LIKE '%"+ subSymbols[0] +"%' or c.code LIKE '%"+ subSymbols[1] + "%') AND";
            }

            query = query.substring(0, query.length() - 3);
        }

        System.out.println(query);
        List<Chord> chords = entityManager.createQuery(query, Chord.class)
                .setParameter("idp", ids)
                .setParameter("expressionBase", expressionBase)
                .getResultList();
        return chords ;
    }

    @Override
    public List<Chord> getChordsOfScaleId(Long id) {
        return entityManager.createQuery("" +
                        "select distinct c" +
                        " from ScaleGrade sg" +
                        " inner join sg.chordOfScale c" +
                        " where sg.scale.id=:idp", Chord.class)
                .setParameter("idp", id)
                .getResultList();
    }

    @Override
    public List<Chord> getChordsOfScalesId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "select distinct c" +
                        " from Scale_Grades sg" +
                        " inner join sg.chord c" +
                        " where sg.scale.id in (:ids)", Chord.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public Optional<Chord>  getChordWithIntervals(List<Interval> intervals) {
        List<Long> ids = new ArrayList<>();
        List<String> expressions = new ArrayList<>();
        String expression = "1";

        for(Interval it: intervals){
            ids.add(it.getId());
            String symbol = it.getSymbol();
            if(symbol.contains("/")) {
                expressions.add(symbol);
                expression += "%__%";
            }
            else {
                expression += "%-"+it.getSymbol();
            }
        }
        if(expression.endsWith("-") )
            expression = expression.substring(0, expression.length()-1);

        System.out.println(expression);
       return getChordWithIntervalsId(ids, expression, expressions);
    }

    @Override
    public Optional<Chord> getChordWithIntervalsId(List<Long> ids, String expressionBase, List<String> expressions) {
        String query = "SELECT c" +
                " FROM Chord c" +
                " INNER JOIN c.chordIntervals ci" +
                " INNER JOIN ci.intervalOfChord i" +
                " WHERE i.id IN (:idp)" +
                " AND c.code LIKE ((:expressionBase)) ";

        if(!expressions.isEmpty()) {
            query += "AND ";
            for(String it: expressions){
                String subSymbols[] = it.split("/");
                query += "(c.code LIKE '%"+ subSymbols[0] +"%' or c.code LIKE '%"+ subSymbols[1] + "%') AND";
            }

            query = query.substring(0, query.length() - 3);
        }

        System.out.println(query);
        List<Chord> chord = entityManager.createQuery(query, Chord.class)
                .setParameter("idp", ids)
                .setParameter("expressionBase", expressionBase)
                .getResultList();
        if( chord.isEmpty() ) return Optional.empty();
        return Optional.of( chord.get(0) );
    }

}
