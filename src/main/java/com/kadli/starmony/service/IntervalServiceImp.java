package com.kadli.starmony.service;

import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.repository.IntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("IntervalService")
public class IntervalServiceImp implements IntervalService{

    @Autowired
    private IntervalRepository intervalRepository;

    @Override
    public List<Interval> getIntervals() {
        return intervalRepository.findAll();
    }

    @Override
    public Optional<Interval> getInterval(Long id) {
        return intervalRepository.findById(id);
    }
}
