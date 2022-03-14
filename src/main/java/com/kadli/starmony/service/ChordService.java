package com.kadli.starmony.service;

import com.kadli.starmony.entity.Chord;

import java.util.List;
import java.util.Optional;

public interface ChordService {

    /**
     * Regresa todos los acordes de la base de datos
     * @return lista de acordes
     */
    List<Chord> getChords();

    /**
     * Obtiene un acorde por la id
     * @param id del acorde
     * @return acorde
     */
    Optional<Chord> getChord(Long id);

    /**
     * Guarda un acorde en la base de datos
     * @param chord acorde a guardar
     */
    void saveChord(Chord chord);
    void deleteChord(Chord chord);


}
