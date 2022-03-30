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
public class IntervalRepositoryCustomImp implements IntervalRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Interval> getIntervalsOfChord(Chord chord) {
        return getIntervalsOfChordId(chord.getId());
    }

    @Override
    public List<Interval> getIntervalsOfChords(List<Chord> chords) {
        List<Long> ids = new ArrayList<>();
        for (Chord it: chords)
            ids.add(it.getId());
        return getIntervalsOfChordsId(ids);
    }

    @Override
    public Interval getIntervalOfNotes(List<Note> notes) {
        List<Long> ids = new ArrayList<>();
        for(Note it: notes)
            ids.add(it.getId());
        return getIntervalOfNotesId(ids);
    }

    @Override
    public List<Interval> getIntervalsOfNotes(List<Note> notes) {
        List<Long> ids = new ArrayList<>();
        for(Note it: notes)
            ids.add(it.getId());
        return getIntervalsOfNotesId(ids);
    }

    @Override
    public Interval getIntervalWithSemitone(Integer semitoneId) {
        return (Interval) entityManager.createQuery("" +
                "from Interval i" +
                " where i.semitones=:semitoneId")
                .setParameter("semitoneId", semitoneId)
                .getResultList().get(0);
    }

    @Override
    public List<Interval> getIntervalsWithSemitones(List<Integer> semitonesId) {
        return entityManager.createQuery("" +
                        "from Interval i" +
                        " where i.semitones in (:semitonesId)")
                .setParameter("semitonesId", semitonesId)
                .getResultList();
    }

    private ArrayList<Integer> getAllSemitonesOfScale(Scale scale){
        String[] codeString = scale.getCode().split("–");
        ArrayList<Integer> code = new ArrayList<>();
        code.add(0);

        for(int i = 0; i < codeString.length; i++)
            code.add(Integer.parseInt(codeString[i]) + code.get(i));

        ArrayList<Integer> semitones = new ArrayList<>();
        for(int i = 0; i < code.size() - 1; i++)
            for(int j = i + 1; j < code.size(); j++)
                semitones.add(code.get(j) - code.get(i));

        return semitones;
    }

    private ArrayList<Integer> getCodeSemitonesOfScale(Scale scale){
        String[] codeString = scale.getCode().split("–");
        ArrayList<Integer> code = new ArrayList<>();
        code.add(0);

        for(int i = 0; i < codeString.length; i++)
            code.add(Integer.parseInt(codeString[i]) + code.get(i));

        return code;
    }

    @Override
    public List<Interval> getIntervalsOfScale(Scale scale) {
        return getIntervalsWithSemitones(getCodeSemitonesOfScale(scale).stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public List<Interval> getIntervalsOfScales(List<Scale> scales) {
        ArrayList<Integer> semitones = new ArrayList<>();
        for(Scale it: scales)
            semitones.addAll(getCodeSemitonesOfScale(it));
        return getIntervalsWithSemitones(semitones.stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public List<Interval> getAllIntervalsOfScale(Scale scale) {
        return getIntervalsWithSemitones(getAllSemitonesOfScale(scale).stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public List<Interval> getAllIntervalsOfScales(List<Scale> scales) {
        ArrayList<Integer> semitones = new ArrayList<>();
        for(Scale it: scales)
            semitones.addAll(getAllSemitonesOfScale(it));
        return getIntervalsWithSemitones(semitones.stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public List<Interval> getIntervalsOfChordId(Long id) {
        return entityManager.createQuery("" +
                "select distinct i" +
                " from Interval i" +
                " inner join i.chords c where c.id="+id)
                .getResultList();
    }

    @Override
    public List<Interval> getIntervalsOfChordsId(List<Long> ids) {
        return entityManager.createQuery("" +
                "select distinct i" +
                " from Interval i" +
                " inner join i.chords c where c.id in (:ids)")
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public Interval getIntervalOfNotesId(List<Long> ids) {
        return (Interval) entityManager.createQuery("" +
                "select distinct i" +
                " from Notes_Has_Intervals nhi" +
                " inner join nhi.interval i" +
                " where nhi.note1.id=:id1 and nhi.note2.id=:id2")
                .setParameter("id1", ids.get(0))
                .setParameter("id2", ids.get(1))
                .getResultList().get(0);
    }

    @Override
    public List<Interval> getIntervalsOfNotesId(List<Long> ids) {
        return entityManager.createQuery("" +
                        "select distinct i" +
                        " from Notes_Has_Intervals nhi" +
                        " inner join nhi.interval i" +
                        " where nhi.note1.id in (:ids) and nhi.note2.id in (:ids)")
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public List<Interval> getIntervalsOfScaleId(Long id) {
        List<Scale> scales = entityManager.createQuery("from Scale s where s.id="+id).getResultList();
        return getIntervalsOfScale(scales.get(0));
    }

    @Override
    public List<Interval> getIntervalsOfScalesId(List<Long> ids) {
        List<Scale> scales = entityManager.createQuery("from Scale s where s.id in (:ids)")
                .setParameter("ids", ids)
                .getResultList();
        return getIntervalsOfScales(scales.stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public List<Interval> getAllIntervalsOfScaleId(Long id) {
        List<Scale> scales = entityManager.createQuery("from Scale s where s.id="+id)
                .getResultList();
        return getAllIntervalsOfScale(scales.get(0));
    }

    @Override
    public List<Interval> getAllIntervalsOfScalesId(List<Long> ids) {
        List<Scale> scales = entityManager.createQuery("from Scale s where s.id in (:ids)")
                .setParameter("ids", ids)
                .getResultList();
        return getAllIntervalsOfScales(scales.stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public Optional<Interval> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Interval> findBySymbol(String symbol) {
        return Optional.empty();
    }
}
