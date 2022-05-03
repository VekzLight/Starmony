package com.kadli.starmony.entity;

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
    private ConcreteScaleId id = new ConcreteScaleId();

    @ManyToOne
    @JoinColumn(name = "scale_id_scale")
    private Scale scaleOfNotes;

    @ManyToOne
    @JoinColumn(name = "note_id_note")
    private Note notesOfScale;


}
