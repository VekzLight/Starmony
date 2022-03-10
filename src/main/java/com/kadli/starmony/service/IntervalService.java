package com.kadli.starmony.service;

import com.kadli.starmony.entity.Interval;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IntervalService {

    List<Interval> getIntervals();
    Optional<Interval> getInterval(Long id);
}
