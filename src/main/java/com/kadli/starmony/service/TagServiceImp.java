package com.kadli.starmony.service;

import com.kadli.starmony.dto.*;
import com.kadli.starmony.entity.Tag;
import com.kadli.starmony.entity.TagProgression;
import com.kadli.starmony.entity.TagScale;
import com.kadli.starmony.repository.TagProgressionRepository;
import com.kadli.starmony.repository.TagRespository;
import com.kadli.starmony.repository.TagScaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("TagService")
public class TagServiceImp implements TagService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TagRespository tagRespository;

    @Autowired
    private TagScaleRepository tagScaleRepository;

    @Autowired
    private TagProgressionRepository tagProgressionRepository;

    @Override
    public TagDTO entityToDTO(Tag entity) {
        return modelMapper.map(entity, TagDTO.class);
    }

    @Override
    public Tag dtotoEntity(TagDTO dto) {
        return modelMapper.map(dto, Tag.class);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRespository.findAll();
    }

    @Override
    public Optional<Tag> getTagById(Long idTag) {
        return tagRespository.findById(idTag);
    }

    @Override
    public List<TagScale> getAllTagsScale() {
        return tagScaleRepository.getTagsScales();
    }

    @Override
    public List<TagProgression> getAllTagsProgressions() { return tagProgressionRepository.getAllTagsProgressions(); }


    @Override
    public Optional<TagProgressionDTO> entityToTagProgressionDTO(TagProgression tagProgression) {
        TagProgressionDTO tagProgressionDTO = new TagProgressionDTO();
        TagDTO tagDTO = modelMapper.map(tagProgression.getTagOfProgression(), TagDTO.class);
        ProgressionDTO progressionDTO = modelMapper.map( tagProgression.getProgressionOfTag(), ProgressionDTO.class );

        tagProgressionDTO.setTagDTO(tagDTO);
        tagProgressionDTO.setProgressionDTO(progressionDTO);
        return Optional.of(tagProgressionDTO);
    }

    @Override
    public Optional<TagScaleDTO> entityToTagScaleDTO(TagScale tagScale) {
        TagScaleDTO tagScaleDTO = new TagScaleDTO();
        TagDTO tagDTO = modelMapper.map(tagScale.getTagOfScale(), TagDTO.class);
        ScaleDTO scaleDTO = modelMapper.map(tagScale.getScaleOfTag(), ScaleDTO.class);

        tagScaleDTO.setTagDTO(tagDTO);
        tagScaleDTO.setScaleDTO(scaleDTO);

        return Optional.of(tagScaleDTO);
    }


}
