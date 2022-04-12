package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ChordIntervals;
import com.kadli.starmony.entity.ChordIntervalsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChordIntervalRepository extends JpaRepository<ChordIntervals, ChordIntervalsId> {
}
