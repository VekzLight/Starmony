package com.kadli.starmony.repository;

import com.kadli.starmony.entity.ScaleInterval;
import com.kadli.starmony.entity.ScaleIntervalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScaleIntervalRepository extends JpaRepository<ScaleInterval, ScaleIntervalId> {

    @Query("SELECT MAX(si.id.id_scale_interval) FROM ScaleInterval si")
    Long getLastId();
}
