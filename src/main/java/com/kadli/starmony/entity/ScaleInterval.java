package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "scale_interval")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleInterval {

    @EmbeddedId
    private ScaleIntervalId id = new ScaleIntervalId();

    @ManyToOne
    @JoinColumn(name = "scale_id_scale")
    private Scale scaleOfInterval;

    @ManyToOne
    @JoinColumn(name = "interval_id_interval")
    private Interval intervalOfScale;
}
