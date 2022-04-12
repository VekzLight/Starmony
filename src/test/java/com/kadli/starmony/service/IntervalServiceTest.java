package com.kadli.starmony.service;

import com.kadli.starmony.StarmonyApplication;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.repository.NoteRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = StarmonyApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntervalServiceTest {

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private ChordService chordService;

    @Test
    @Transactional
    void generateAllIntervalsOfChordsAndSave() {
        intervalService.generateAllIntervalsOfChordsAndSave();
    }

    @Test
    @Transactional
    void getIntervalsOfChord() {
        Chord chord = chordService.getById(1L).get();
        List<Interval> intervals = intervalService.getIntervalsOfChord(chord);
        for(Interval interval: intervals)
            System.out.println(interval.getSymbol() + " - ");
        System.out.println("hola");
    }
}