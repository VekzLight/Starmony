package com.kadli.starmony.service;

import com.kadli.starmony.entity.Scale;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ScaleService {

    List<Scale> getScales();
    Optional<Scale> getScale(Long id);
}
