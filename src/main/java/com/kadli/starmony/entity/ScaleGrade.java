package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private ScaleGradesId sg_id = new ScaleGradesId();

    @MapsId("id_scale")
    @ManyToOne
    @JoinColumn(name = "scale_id_scale")
    private Scale scale_chord;

    @MapsId("id_chord")
    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chord_scale;

    @Column(name = "grade")
    private String grade;
}
