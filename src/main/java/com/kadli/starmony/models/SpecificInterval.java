package com.kadli.starmony.models;

import java.util.ArrayList;

public class SpecificInterval extends Interval{

    ArrayList<Note> notes;

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "SpecificInterval{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", notes=" + notes +
                '}';
    }
}
