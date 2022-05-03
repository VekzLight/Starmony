package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ProgressionGradeId implements Serializable {

    @Column(name = "id_progression_grade")
    private Long id_progression_grade;

    @Column(name = "position_grade")
    private int position;


}