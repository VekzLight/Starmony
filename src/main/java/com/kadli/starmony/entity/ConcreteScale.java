package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "concrete_scale")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcreteScale {

    @EmbeddedId
    private ChordIntervalsId cs_id = new ChordIntervalsId();

    @MapsId("id_scale")
    @ManyToOne
    @JoinColumn(name = "scale_id_scale")
    private Scale scale_note;

    @MapsId("id_note")
    @ManyToOne
    @JoinColumn(name = "note_id_note")
    private Note note_scale;


}
