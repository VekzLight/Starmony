package com.kadli.starmony.service;

import com.kadli.starmony.StarmonyApplication;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Note;
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
class ConcreteChordServiceImpTest {

    @Autowired
    private ConcreteChordService concreteChordService;

    @Autowired
    private ChordService chordService;

    @Autowired
    private NoteService noteService;

    @Test
    @Transactional
    void generateAndSaveConcreteChordsWithNote() {

    }
}