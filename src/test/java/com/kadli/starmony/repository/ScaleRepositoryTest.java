package com.kadli.starmony.repository;

import com.kadli.starmony.StarmonyApplication;
import com.kadli.starmony.entity.Scale;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = StarmonyApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScaleRepositoryTest {

    @Autowired
    private ScaleRepository scaleRepository;

    @Test
    @Transactional
    @Order(1)
    @Rollback(value = false)
    public void getScalesWithIntervalIdTest(){
        List<Scale> scales = scaleRepository.getScalesWithIntervalId(1L);
        System.out.println(scales.toString());
        Assertions.assertEquals(2, scales.size());
    }

    @Test
    @Transactional
    @Order(2)
    @Rollback(value = false)
    public void getScalesWithIntervalsIdTest(){
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(8L);
        List<Scale> scales = scaleRepository.getScalesWithIntervalsId(ids);
        System.out.println(scales.toString());
        Assertions.assertEquals(2, scales.size());
    }

    @Test
    @Transactional
    @Order(3)
    @Rollback(value = false)
    public void getScalesWithChordIdTest(){
        List<Scale> scales = scaleRepository.getScalesWithChordId(3L);
        System.out.println(scales.toString());
        Assertions.assertEquals(2, scales.size());
    }

    @Test
    @Transactional
    @Order(4)
    @Rollback(value = false)
    public void getScalesWithChordsIdTest(){
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(5L);
        List<Scale> scales = scaleRepository.getScalesWithChordsId(ids);
        System.out.println(scales.toString());
        Assertions.assertEquals(2, scales.size());
    }

    @Test
    @Transactional
    @Order(5)
    @Rollback(value = false)
    public void getScaleWithCodeTest(){
        Scale scale = scaleRepository.getScaleWithCode("2–2–1–2–2–2–1").get();
        System.out.println(scale.toString());
        Assertions.assertEquals(1, scale.getId());
    }
}