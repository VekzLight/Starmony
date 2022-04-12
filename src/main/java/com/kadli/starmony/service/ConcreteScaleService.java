package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.entity.ConcreteScale;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.List;
import java.util.Optional;

public interface ConcreteScaleService {

    // Obtener
    Optional<ConcreteScale> getConcreteScale(Scale scale, Note tonic);
    List<ConcreteScale> getConcreteScaleByScale(Scale scale);


    // Guardar
    void saveConcreteScale(ConcreteScale concreteScale);


    // Utilidades
    Long getLastConcreteScaleId();


    // Generar
    Optional<ConcreteScale> generateConcreteScale(Scale scale, Note note, Long id, int position);
    List<ConcreteScale> generateCompleteConcreteScales(Scale scale, Note tonic);
    List<ConcreteScale> generateAllConcreteScalesAndSave();


    // Conversiones
    ConcreteScaleDTO concreteScalesToConcreteScaleDTO(List<ConcreteScale> concreteScales);
}
