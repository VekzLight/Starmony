package com.kadli.starmony.service;

import com.kadli.starmony.dto.TagDTO;
import com.kadli.starmony.dto.TagProgressionDTO;
import com.kadli.starmony.dto.TagScaleDTO;
import com.kadli.starmony.entity.Tag;
import com.kadli.starmony.entity.TagProgression;
import com.kadli.starmony.entity.TagScale;

import java.util.List;
import java.util.Optional;

public interface TagService extends DtoConversions<Tag, TagDTO>{

    List<Tag> getAllTags();
    Optional<Tag> getTagById(Long idTag);

    List<TagScale> getAllTagsScale();

    Optional<TagScaleDTO> entityToTagScaleDTO(TagScale tagScale);

    List<TagProgression> getAllTagsProgressions();

    Optional<TagProgressionDTO> entityToTagProgressionDTO(TagProgression tagProgression);
}
