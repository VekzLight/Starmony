package com.kadli.starmony.service;

import com.kadli.starmony.StarmonyApplication;
import com.kadli.starmony.entity.ConcreteInterval;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest(classes = StarmonyApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConcreteMusicalElementsServiceImpTest {
    @Autowired
    private ConcreteMusicalElementsService concreteMusicalElementsService;

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private NoteService noteService;


    @Test
    @Transactional
    void generateAllIntervalsOfChordsAndSave() {
        Interval interval = intervalService.getById(2L).get();
        Note tonic = noteService.getById(1L).get();
        Note note = noteService.getById(2L).get();

        ConcreteInterval concreteInterval = concreteMusicalElementsService.getConcreteInterval(interval, tonic, note);
        System.out.println( concreteInterval.getFirstNote().getName());
    }

    @Test
    @Transactional
    void generateCompleteConcreteScales() {
        Scale scale = scaleService.getById(2L).get();
        Note tonic = noteService.getById(1L).get();

        concreteMusicalElementsService.generateCompleteConcreteScales(scale, tonic);
    }

    @Test
    @Transactional
    void generateAllConcreteScalesAndSave() {
        concreteMusicalElementsService.generateAllConcreteScalesAndSave();
    }
}