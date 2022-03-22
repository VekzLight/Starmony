package com.kadli.starmony.dto;

import com.kadli.starmony.entity.Chord;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

public class ProgressionDTO {
    protected Long id;
    protected String name;
    protected String symbol;
    protected String code;
    private List<Chord> chords;
    private List<String> tonality;
}
