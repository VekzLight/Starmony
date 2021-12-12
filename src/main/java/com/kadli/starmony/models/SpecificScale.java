package com.kadli.starmony.models;

import java.util.ArrayList;

public class SpecificScale extends Scale {

    private Note tonic;
    private ArrayList<Note> notes;
    private ArrayList<Integer> code;

    public Note getTonic() {
        return tonic;
    }

    public void setTonic(Note tonic) {
        this.tonic = tonic;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public ArrayList<Integer> getCode() {
        return code;
    }

    public void setCode(ArrayList<Integer> code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SpecificScale{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", tonic=" + tonic +
                ", notes=" + notes +
                ", code=" + code +
                '}';
    }
}
