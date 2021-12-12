package com.kadli.starmony.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class SpecificChord extends Chord {

    @Getter @Setter private Note tonic;
    @Getter @Setter private ArrayList<Note> notes;
    @Getter @Setter private ArrayList<Interval> intervals;

}
