package com.kadli.starmony.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScaleGradeId implements Serializable {

    @Column(name = "id_scale_grade")
    private Long id_scale_grade;

    @Column(name = "grade")
    private String grade;

}
