package com.kadli.starmony.models;

import java.util.ArrayList;

public class SpecificHarmony extends Harmony {

    private ArrayList<Chord> chords;

    public ArrayList<Chord> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Chord> chords) {
        this.chords = chords;
    }

    @Override
    public String toString() {
        return "SpecificHarmony{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", chords=" + chords +
                '}';
    }
}
