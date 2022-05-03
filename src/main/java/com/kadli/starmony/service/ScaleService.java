package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;

import java.util.List;

public interface ScaleService extends CustomCrudService<Scale, Long>, DtoConversions<Scale, ScaleDTO>{

    List<Scale> getScalesWithIntervals(List<Interval> intervals);
    List<Scale> getScalesWithChords(List<Chord> chords);

}
