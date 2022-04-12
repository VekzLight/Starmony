package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.Scale;

public interface ScaleService extends CustomCrudService<Scale, Long>, DtoConversions<Scale, ScaleDTO>{

}
