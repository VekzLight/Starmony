package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRespository extends JpaRepository<Tag, Long> {

}
