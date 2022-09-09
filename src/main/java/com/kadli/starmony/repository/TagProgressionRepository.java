package com.kadli.starmony.repository;

import com.kadli.starmony.entity.Progression;
import com.kadli.starmony.entity.TagProgression;
import com.kadli.starmony.entity.TagProgressionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagProgressionRepository extends JpaRepository<TagProgression, TagProgressionId> {

    @Query("SELECT DISTINCT p.id FROM TagProgression tp INNER JOIN tp.tagOfProgression t INNER JOIN tp.progressionOfTag p WHERE t.id = :idTag")
    public List<Long> getIdProgressionsOfIdTag(@Param("idTag") Long idTag);

    @Query("FROM TagProgression")
    List<TagProgression> getAllTagsProgressions();
}
