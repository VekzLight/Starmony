package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Scale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long>, ScaleRepositoryCustom {

}
