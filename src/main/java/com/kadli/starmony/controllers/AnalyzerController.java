package com.kadli.starmony.controllers;

import com.kadli.starmony.managers.ChordAnalyzer;
import com.kadli.starmony.models.Interval;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyzerController {

    @RequestMapping(value = "getIntervals/{idChord}")
    public List<Interval> getIntervalsOfChord(@PathVariable Integer id){
        ChordAnalyzer analyzer = new ChordAnalyzer();
        return analyzer.analyzeIntervals(id);
    };

}
