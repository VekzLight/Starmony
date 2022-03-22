package com.kadli.starmony.service;

import com.kadli.starmony.entity.Progression;

import java.util.List;
import java.util.Optional;

public interface ProgressionService {

    List<Progression> getProgressions();
    Optional<Progression> getProgresion(Long id);

}
