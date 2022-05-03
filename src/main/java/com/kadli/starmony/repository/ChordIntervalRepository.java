package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ChordInterval;
import com.kadli.starmony.entity.ChordIntervalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChordIntervalRepository extends JpaRepository<ChordInterval, ChordIntervalId> {

    @Query( "SELECT MAX(ci.id.id_chord_interval) FROM ChordInterval ci" )
    Long getLastId();
}
