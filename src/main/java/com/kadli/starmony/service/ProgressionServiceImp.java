package com.kadli.starmony.service;

import com.kadli.starmony.entity.Progression;
import com.kadli.starmony.repository.ProgressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ProgressionService")
public class ProgressionServiceImp implements ProgressionService{

    @Autowired
    private ProgressionRepository progressionRepository;


    @Override
    public List<Progression> getProgressions() {
        return progressionRepository.findAll();
    }

    @Override
    public Optional<Progression> getProgresion(Long id) {
        return progressionRepository.findById(id);
    }
}
