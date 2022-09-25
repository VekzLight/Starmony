package com.kadli.starmony.repository;

import com.kadli.starmony.entity.TagScale;
import com.kadli.starmony.entity.TagScaleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagScaleRepository extends JpaRepository<TagScale, TagScaleId> {

    @Query("FROM TagScale")
    public List<TagScale> getTagsScales();

}
