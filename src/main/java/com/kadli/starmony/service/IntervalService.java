package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.IntervalDTO;
import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IntervalService extends CustomConcreteService<Interval, Long> {

    IntervalDTO toDTO(Interval interval);

    List<Interval> getIntervalsOfScale(Scale scale);
    List<Interval> getAllIntervalsOfScale(Scale scale);
}
