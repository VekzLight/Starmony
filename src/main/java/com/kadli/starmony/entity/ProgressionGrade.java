package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "progression_grade")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgressionGrade {

    @EmbeddedId
    private ProgressionGradeId id = new ProgressionGradeId();

    @ManyToOne
    @JoinColumn(name = "id_progression")
    private Progression progressionOfProgressionGrade;

    @ManyToOne
    @JoinColumns({
            @JoinColumn( name = "id_scale_grade", referencedColumnName = "id_scale_Grade"),
            @JoinColumn( name = "grade", referencedColumnName = "grade")
    })
    private ScaleGrade scaleGradeOfProgression;

}
