package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "scale_grade")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScaleGrade {

    @EmbeddedId
    private ScaleGradesId id = new ScaleGradesId();

    @MapsId("id_scale")
    @ManyToOne
    @JoinColumn(name = "scale_id_scale")
    private Scale scaleOfChord;

    @MapsId("id_chord")
    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chordOfScale;

    @Column(name = "grade")
    private String grade;
}
