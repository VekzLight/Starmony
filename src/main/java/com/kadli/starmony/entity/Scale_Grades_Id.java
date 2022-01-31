package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scale_Grades_Id implements Serializable {

    @Column(name = "scale_id_scale")
    private Long id_scale;

    @Column(name = "chord_id_chord")
    private Long id_chord;

}
