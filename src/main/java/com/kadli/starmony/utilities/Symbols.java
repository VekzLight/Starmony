package com.kadli.starmony.utilities;

import com.kadli.starmony.entity.*;

import javax.persistence.Column;

public class Symbols {
    public final static String SYMBOL_SEPARATION_SCALE = "–";
    public final static String SYMBOL_SEPARATION_PROGRESSION = "-";

    public final static String POS_TO_GRADE(int pos){
        String grade = "ZERO";
        switch (pos){
            case 1: grade = "I"; break;
            case 2: grade = "II"; break;
            case 3: grade = "III"; break;
            case 4: grade = "IV"; break;
            case 5: grade = "V"; break;
            case 6: grade = "VI"; break;
            case 7: grade = "VII"; break;
            case 8: grade = "VIII"; break;
            case 9: grade = "IX"; break;
            case 10: grade = "X"; break;
        }
        return grade;
    }

    public final static int GRADE_TO_POS(String grade){
        int pos = 0;
        switch (grade){
            case "I":   pos = 1; break;
            case "II":  pos = 2; break;
            case "III": pos = 3; break;
            case "IV":  pos = 4; break;
            case "V":   pos = 5; break;
            case "VI":  pos = 6; break;
            case "VII": pos = 7; break;
            case "VIII":pos = 8; break;
            case "IX":  pos = 9; break;
            case "X":   pos = 10; break;
        }
        return pos;
    }

    public static ScaleGrade getScaleGradeWithGrade( String grade, Long idScaleGrade ){ return ScaleGrade.builder().exist(false).chordOfScale(chord).id( new ScaleGradeId(idScaleGrade, grade) ).build(); }

    public final static Chord chord = Chord.builder().id(-1L).name("Desconocido").symbol("?").build();
    public final static ScaleGrade scaleGrade = ScaleGrade.builder().exist(false).chordOfScale(chord).build();
    public final static ConcreteScaleGrade concreteScaleGrade = ConcreteScaleGrade.builder().exist(false).scaleGrade(scaleGrade).build();

    public static Long nextIdProgressionConcrete = -1L;
}
