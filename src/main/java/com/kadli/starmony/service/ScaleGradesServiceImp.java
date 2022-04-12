package com.kadli.starmony.service;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Interval;
import com.kadli.starmony.entity.Scale;
import com.kadli.starmony.entity.ScaleGrade;
import com.kadli.starmony.repository.ChordRepository;
import com.kadli.starmony.repository.IntervalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ScaleGradesService")
public class ScaleGradesServiceImp implements ScaleGradesService{

    @Autowired
    private ChordRepository chordRepository;

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ScaleGrade> generateGradesOfScale(Scale scale){
        List<Integer> code = new ArrayList<>();
        for(String codeString: scale.getCode().split("-"))
            code.add(Integer.parseInt(codeString));

        List<ScaleGrade> scaleGrades = new ArrayList<>();
        System.out.println(code.toString());
        for(int i = 0; i < code.size(); i++){
            ScaleGrade scaleGrade = new ScaleGrade();
            scaleGrade.setScaleOfChord(scale);
            List<Integer> semitonesI = new ArrayList<>();

            int semitonesCounter = 0;


            semitonesCounter += code.get(i);
            semitonesCounter += (i+1 > 6) ? code.get(0): code.get(i+1);
            semitonesI.add(semitonesCounter);

            semitonesCounter += (i+2 > 6) ? code.get(i+2-6) : code.get(i+2);
            semitonesCounter += (i+3 > 6) ? code.get(i+3-6) : code.get(i+3);
            semitonesI.add(semitonesCounter);

            List<Interval> intervalsI = intervalRepository.getIntervalsWithSemitones(semitonesI);
            Chord gradeI = chordRepository.getChordWithIntervals(intervalsI).get(0);
            scaleGrade.setChordOfScale(gradeI);

            scaleGrade.setGrade("I");
            scaleGrades.add(scaleGrade);
        }

        return scaleGrades;
    }

}
