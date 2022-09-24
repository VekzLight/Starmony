package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "concrete_scale_grade")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcreteScaleGrade {

    @EmbeddedId
    private ConcreteScaleGradeId id = new ConcreteScaleGradeId();

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_concrete_scale", referencedColumnName = "id_concrete_scale", insertable = false, updatable = false),
            @JoinColumn(name = "position_note_scale", referencedColumnName = "position_note_scale", insertable = false, updatable = false)
    })
    private ConcreteScale concreteScale;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_scale_grade", referencedColumnName = "id_scale_grade", insertable = false, updatable = false),
            @JoinColumn(name = "grade", referencedColumnName = "grade", insertable = false, updatable = false)
    })
    private ScaleGrade scaleGrade;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_concrete_chord", referencedColumnName = "id_concrete_chord", insertable = false, updatable = false),
            @JoinColumn(name = "position_note_chord", referencedColumnName = "position_note_chord", insertable = false, updatable = false)
    })
    private ConcreteChord concreteChord;

    @JsonIgnore
    @Transient
    private Boolean exist;
}
