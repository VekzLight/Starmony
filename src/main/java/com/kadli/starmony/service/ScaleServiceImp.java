package com.kadli.starmony.service;

import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.repository.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("ScaleService")
public class ScaleServiceImp implements ScaleService{

    @Autowired
    private ScaleRepository scaleRepository;

    @Override
    public List<Scale> getScales() {
        return scaleRepository.findAll();
    }

    @Override
    public Optional<Scale> getScale(Long id) {
        return scaleRepository.findById(id);
    }
}
