package com.kadli.starmony.service;

import com.kadli.starmony.dto.ConcreteScaleDTO;
import com.kadli.starmony.entity.ConcreteScale;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;

import java.util.List;
import java.util.Optional;

public interface ConcreteScaleService {

    // Obtener
    List<ConcreteScale> getAll();
    List<ConcreteScale> getCompleteConcreteScaleById(Long id);
    List<ConcreteScale> getCompleteConcreteScaleWithTonic(Long idScale, Long idTonic);
    Optional<ConcreteScale> getConcreteScaleWithTonic(Long idscale, Long idTonic);

    List<Long> getAllIds();


    // Guardar
    void saveConcreteScale(ConcreteScale concreteScale);


    // Utilidades
    Long getLastConcreteScaleId();
    Long getIdConcreteScale( Long idScale, Long idNote );

    // Generar
    Optional<ConcreteScale> generateConcreteScale(Scale scale, Note note, Long id, int position);
    List<ConcreteScale> generateCompleteConcreteScales(Scale scale, Note tonic);
    List<ConcreteScale> generateCompleteConcreteScalesAndSave(Scale scale, Note tonic);
    List<ConcreteScale> generateAllConcreteScalesAndSave();



    // Conversiones
    ConcreteScaleDTO concreteScalesToConcreteScaleDTO(List<ConcreteScale> concreteScales);

    List<ConcreteScaleDTO> getConcreteScalesWithTonicAndNotes(List<Long> idNotes, Long idTonic);

    List<Long> getIdConcreteScalesWithConcreteChords(List<Long> idConcreteChords);

}
