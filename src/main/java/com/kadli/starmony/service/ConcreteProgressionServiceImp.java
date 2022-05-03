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
    public List<ConcreteProgression> generateConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades) {

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

            concreteProgressions.add( concreteProgression );
        }

        return concreteProgressions;
    }

    @Override
    public List<ConcreteProgression> generateAndSaveConcreteProgression(List<ProgressionGrade> progressionGrades, List<ConcreteScaleGrade> concreteScaleGrades) {
        List<ConcreteProgression> concreteProgressions = this.generateConcreteProgression(progressionGrades, concreteScaleGrades);
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
                    List<ConcreteScaleGrade> concreteScaleGrades = concreteChordService.getCompleteConcreteScaleGradesByConcreteScaleId( idConcreteScale );

                    if(concreteScaleGrades.isEmpty()) continue;
                    concreteProgressions.addAll(this.generateAndSaveConcreteProgression( progressionGrades, concreteScaleGrades));
                }
            }
        }
        return concreteProgressions;
    }

    @Override
    public Long getMaxId() {
        Long id = concreteProgressionRepository.getLastId();
        return id == null ? 0 : id;
    }

    @Override
    public Optional<ConcreteProgressionDTO> concreteProgressionToConcreteProgressionDTO(List<ConcreteProgression> concreteProgressions) {
        ConcreteProgressionDTO concreteProgressionDTO = new ConcreteProgressionDTO();

        Progression progression = concreteProgressions.get(0).getProgressionGrade().getProgressionOfProgressionGrade();
        Long id_progression_grade = concreteProgressions.get(0).getProgressionGrade().getId().getId_progression_grade();

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

}
