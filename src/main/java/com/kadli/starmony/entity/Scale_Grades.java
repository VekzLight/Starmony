package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "scale_grades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scale_Grades {

    @EmbeddedId
    private Scale_Grades_Id sg_id = new Scale_Grades_Id();

    @MapsId("id_scale")
    @ManyToOne
    @JoinColumn(name = "scale_id_scale")
    private Scale scale;

    @MapsId("id_chord")
    @ManyToOne
    @JoinColumn(name = "chord_id_chord")
    private Chord chord;

    @Column(name = "grade")
    private String grade;
}
