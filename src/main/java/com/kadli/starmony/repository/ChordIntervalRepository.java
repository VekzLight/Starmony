package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ChordInterval;
import com.kadli.starmony.entity.ChordIntervalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChordIntervalRepository extends JpaRepository<ChordInterval, ChordIntervalId> {

    @Query( "SELECT MAX(ci.id.id_chord_interval) FROM ChordInterval ci" )
    Long getLastId();

    @Query( "FROM ChordInterval ci INNER JOIN ci.intervalOfChord i WHERE i.id in (:idIntervals)" )
    List<ChordInterval> getChordIntervalsWithIntervals(@Param("idIntervals") List<Long> idIntervals);

    @Query( "SELECT DISTINCT ci.id.id_chord_interval FROM ChordInterval ci INNER JOIN ci.intervalOfChord i WHERE i.id in (:idIntervals)" )
    List<Long> getIdChordIntervalsWithIntervals(@Param("idIntervals") List<Long> collect);

    @Query( "FROM ChordInterval ci WHERE ci.id.id_chord_interval = :idChordInterval" )
    List<ChordInterval> getChordIntervalsById(@Param("idChordInterval") Long idChordInterval);
}
