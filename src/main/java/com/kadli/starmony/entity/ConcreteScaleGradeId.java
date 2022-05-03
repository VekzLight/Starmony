package com.kadli.starmony.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ConcreteScaleGradeId implements Serializable {

    @Column(name = "id_concrete_scale_grade")
    private Long id_concrete_scale_grade;

    @Column(name = "id_concrete_scale")
    private Long id_concrete_scale;

    @Column(name = "position_note_scale")
    private int position_note_scale;

    @Column(name = "id_scale_grade")
    private Long id_scale_grade;

    @Column(name = "grade")
    private String grade;

    @Column(name = "id_concrete_chord")
    private Long id_concrete_chord;

    @Column(name = "position_note_chord")
    private int position_note_chord;
}
