package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ConcreteChord;
import com.kadli.starmony.entity.ConcreteChordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcreteChordRepository extends JpaRepository<ConcreteChord, ConcreteChordId> {

    @Query("SELECT max(cci.id.id_concrete_chord) FROM ConcreteChord cci")
    Long getMaxId();

    @Query("SELECT cci.id.id_concrete_chord FROM ConcreteChord cci WHERE cci.id.id_chord = :idChord AND cci.id.id_note = :idNote AND cci.id.position = 1")
    Long getIdConcreteChord(@Param("idChord") Long idChord, @Param("idNote") Long idNote);

    @Query("FROM ConcreteChord cci WHERE cci.id.id_concrete_chord = :idConcrete")
    List<ConcreteChord> getConcreteChordsByIdConcrete(@Param("idConcrete") Long idConcrete);

}
