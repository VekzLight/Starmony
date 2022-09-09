package com.kadli.starmony.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ScaleIntervalId implements Serializable {

    @Column(name = "id_scale_interval")
    private Long id_scale_interval;

    @Column(name = "position_interval")
    private Integer position_interval;
}
