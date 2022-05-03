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
import java.util.Optional;


@SpringBootTest(classes = StarmonyApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConcreteMusicalElementsServiceImpTest {

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private ConcreteIntervalService concreteIntervalService;

    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Test
    @Transactional
    void generateAllIntervalsOfChordsAndSave() {
        Interval interval = intervalService.getById(2L).get();
        Note tonic = noteService.getById(1L).get();

        Optional<ConcreteInterval> concreteInterval = concreteIntervalService.getConcreteIntervalWithTonic(interval.getId(), tonic.getId());
        System.out.println( concreteInterval.get().getFirstNote().getName());
    }

    @Test
    @Transactional
    void generateCompleteConcreteScales() {
        Scale scale = scaleService.getById(2L).get();
        Note tonic = noteService.getById(1L).get();

        concreteScaleService.generateCompleteConcreteScales(scale, tonic);
    }

    @Test
    @Transactional
    void generateAllConcreteScalesAndSave() {
        concreteScaleService.generateAllConcreteScalesAndSave();
    }
}