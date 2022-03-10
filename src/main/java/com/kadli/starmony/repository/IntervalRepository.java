package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Interval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervalRepository extends JpaRepository<Interval, Long>, IntervalRepositoryCustom {

}
