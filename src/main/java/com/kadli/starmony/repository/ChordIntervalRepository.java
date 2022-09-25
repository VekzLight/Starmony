package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.ChordInterval;
import com.kadli.starmony.entity.ChordIntervalId;
import com.kadli.starmony.entity.Interval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query( "SELECT ci.id.id_chord_interval" +
            " FROM ChordInterval ci" +
            " GROUP BY ci.id.id_chord_interval" +
            " HAVING COUNT( ci.id.id_chord_interval ) = :lent" +
            " ORDER BY ci.id.id_chord_interval ASC" )
    List<Long> getChordIntervalsIdsWhereLength(@Param("lent") Long lent);

    @Query( "SELECT c" +
            " FROM ChordInterval ci" +
            " INNER JOIN ci.intervalOfChord i" +
            " INNER JOIN ci.chordOfInterval c" +
            " WHERE ci.id.id_chord_interval IN (:idChordIntervals)" +
            "   AND i.id IN (:idIntervals)" +
            " GROUP BY ci.id.id_chord_interval" +
            " HAVING COUNT(ci) = :lent" )
    Optional<Chord> getChordWithIntervals( @Param("idChordIntervals") List<Long> idChordIntervals, @Param("idIntervals") List<Long> idIntervals, @Param("lent") Long lent);
}
