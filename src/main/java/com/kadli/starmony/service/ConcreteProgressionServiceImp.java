package com.kadli.starmony.service;

import com.kadli.starmony.dto.ChordDTO;
import com.kadli.starmony.dto.ConcreteChordDTO;
import com.kadli.starmony.dto.ConcreteProgressionDTO;
import com.kadli.starmony.dto.ScaleDTO;
import com.kadli.starmony.entity.*;
import com.kadli.starmony.repository.ConcreteProgressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("ConcreteProgressionService")
public class ConcreteProgressionServiceImp implements ConcreteProgressionService {

    @Autowired
    private ProgressionService progressionService;

    @Autowired
    private ChordService chordService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private ScaleService scaleService;



    @Autowired
    private ConcreteScaleService concreteScaleService;

    @Autowired
    private ConcreteChordService concreteChordService;



    @Autowired
    private ConcreteProgressionRepository concreteProgressionRepository;


    @Override
    public List<ConcreteProgression> generateConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades, List<ConcreteScale> concreteScales) {

        List<ConcreteProgression> concreteProgressions = new ArrayList<>();
        HashMap<String, ConcreteChord> concreteChordHashMap = new HashMap<>();

        for(ConcreteScaleGrade concreteScaleGrade: concreteScaleGrades)
            concreteChordHashMap.put( concreteScaleGrade.getId().getGrade(), concreteScaleGrade.getConcreteChord() );

        Long idProgressionGrade = this.getMaxId() + 1;
        for(ProgressionGrade progressionGrade: progressionGrades){
            ConcreteProgressionId concreteProgressionId = new ConcreteProgressionId();
            concreteProgressionId.setId_concrete_progression( idProgressionGrade );
            concreteProgressionId.setPosition_concrete_chord( progressionGrade.getId().getPosition() );

            ConcreteProgression concreteProgression = new ConcreteProgression();
            concreteProgression.setId( concreteProgressionId );
            concreteProgression.setProgressionGrade( progressionGrade );
            concreteProgression.setConcreteChord( concreteChordHashMap.get( progressionGrade.getScaleGradeOfProgression().getId().getGrade() ) );
            concreteProgression.setConcreteScale( concreteScales.get(0) );

            concreteProgressions.add( concreteProgression );
        }

        return concreteProgressions;
    }

    @Override
    public List<ConcreteProgression> generateAndSaveConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades, List<ConcreteScale> concreteScales) {
        List<ConcreteProgression> concreteProgressions = this.generateConcreteProgression(progressionGrades, concreteScaleGrades, concreteScales);
        for(ConcreteProgression it:concreteProgressions)
            concreteProgressionRepository.save(it);
        return concreteProgressions;
    }

    @Override
    public List<ConcreteProgression> generateAndSaveAllConcreteProgressions() {
        List<Progression> progressions = progressionService.getAll();
        List<Scale> scales = scaleService.getAll();
        List<Note> notes = noteService.getAll();

        List<ConcreteProgression> concreteProgressions = new ArrayList<>();
        for(Progression progression: progressions){
            for(Scale scale: scales){
                List<ProgressionGrade> progressionGrades = progressionService.getCompleteProgressionGradeByScaleGrade( progression.getId(), scale.getId() );
                if(progressionGrades.isEmpty()) continue;

                for(Note note: notes){
                    Long idConcreteScale = concreteScaleService.getIdConcreteScale(scale.getId(), note.getId());
                    List<ConcreteScale> concreteScales = concreteScaleService.getCompleteConcreteScaleById(idConcreteScale);
                    List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( idConcreteScale );

                    if(concreteScaleGrades.isEmpty()) continue;
                    concreteProgressions.addAll(this.generateAndSaveConcreteProgression( progressionGrades, concreteScaleGrades, concreteScales));
                }
            }
        }
        return concreteProgressions;
    }

    @Override
    public Long
    getMaxId() {
        Long id = concreteProgressionRepository.getLastId();
        return id == null ? 0 : id;
    }

    @Override
    public Optional<ConcreteProgressionDTO> concreteProgressionToConcreteProgressionDTO(List<ConcreteProgression> concreteProgressions) {
        ConcreteProgressionDTO concreteProgressionDTO = new ConcreteProgressionDTO();

        Progression progression = concreteProgressions.get(0).getProgressionGrade().getProgressionOfProgressionGrade();
        Long id_progression_grade = concreteProgressions.get(0).getProgressionGrade().getId().getId_progression_grade();
        Long id_concrete_scale = concreteProgressions.get(0).getConcreteScale().getId().getId_concrete_scale();

        Scale scale = concreteProgressions.get(0).getProgressionGrade().getScaleGradeOfProgression().getScaleOfChord();
        ScaleDTO scaleDTO = scaleService.entityToDTO(scale);

        Long id_concrete_progression = concreteProgressions.get(0).getId().getId_concrete_progression();

        concreteProgressionDTO.setId(progression.getId());
        concreteProgressionDTO.setName(progression.getName());
        concreteProgressionDTO.setCode(progression.getCode());
        concreteProgressionDTO.setSymbol(progression.getSymbol() );

        concreteProgressionDTO.setScale(scaleDTO);

        concreteProgressionDTO.setId_concrete_progression( id_concrete_progression );
        concreteProgressionDTO.setId_progression_grade( id_progression_grade );
        concreteProgressionDTO.setId_concrete_scale(id_concrete_scale);

        HashMap<Integer, ChordDTO> chordDTOHashMap = new HashMap<>();
        HashMap<Integer, ConcreteChordDTO> concreteChordDTOHashMap = new HashMap<>();

        for(ConcreteProgression concreteProgression: concreteProgressions){
            List<ConcreteChord> concreteChords = concreteChordService.getCompleteConcreteChordById( concreteProgression.getConcreteChord().getCc_id().getId_concrete_chord() );
            Optional<ConcreteChordDTO> concreteChordDTO = concreteChordService.concreteChordToConcreteChordDTO(concreteChords);
            if(!concreteChordDTO.isPresent()) return Optional.empty();

            concreteChordDTOHashMap.put( concreteProgression.getProgressionGrade().getId().getPosition(), concreteChordDTO.get() );

            Chord chord = concreteProgression.getConcreteChord().getConcreteChord();
            ChordDTO chordDTO = chordService.entityToDTO(chord );
            chordDTOHashMap.put( concreteProgression.getProgressionGrade().getId().getPosition(), chordDTO );
        }

        concreteProgressionDTO.setGrades( chordDTOHashMap );
        concreteProgressionDTO.setConcreteGrades( concreteChordDTOHashMap );

        return Optional.of( concreteProgressionDTO );
    }

    @Override
    public List<ConcreteProgression> getCompleteConcreteProgressionById(Long idProgression) {
        return concreteProgressionRepository.getCompleteConcreteProgressionById(idProgression);
    }

    @Override
    public List<ConcreteProgressionDTO> getCompleteConcreteProgressionsByIdScaleAndChord(List<Long> idConcreteScales, List<Long> idConcreteChords, Long idTonic) {
        List<Long> idConcreteProgressions = concreteProgressionRepository.getIdCompleteConcreteProgressionByIdScaleAndChord(idConcreteChords, idConcreteScales);
        List<ConcreteProgressionDTO> concreteProgressionDTOS = new ArrayList<>();
        for(Long id: idConcreteProgressions){
            List<ConcreteProgression> concreteProgressions = concreteProgressionRepository.getCompleteConcreteProgressionById(id);
            boolean flag = false;
            for(ConcreteProgression concreteProgression: concreteProgressions){
                if(concreteProgression.getConcreteScale().getId().getPosition() == 1 && concreteProgression.getConcreteScale().getNotesOfScale().getId() == idTonic)
                    flag = true;
            }
            if(flag = true) concreteProgressionDTOS.add( this.concreteProgressionToConcreteProgressionDTO(concreteProgressions).get() );
        }
        return concreteProgressionDTOS;
    }

    @Override
    public List<ConcreteProgression> getCompleteConcreteProgressionsByProgressionGradeAndConcreteScale(Long idConcreteScale, Long idProgressionGrade) {
        return concreteProgressionRepository.getCompleteConcreteProgressionByPGAndCS(idConcreteScale, idProgressionGrade);
    }

    @Override
    public List<Long> getAllIds() {
        return concreteProgressionRepository.getAllIds();
    }

    @Override
    public List<Long> getConcreteProgressionsIdsByConcreteScaleId(Long concreteScaleId) {
        return concreteProgressionRepository.getConcreteProgressionsIdsByConcreteScaleId(concreteScaleId);
    }

    @Override
    public void saveAllProgressionGrades(List<ConcreteProgression> concreteProgressions) {
        concreteProgressionRepository.saveAll(concreteProgressions);
    }

}
