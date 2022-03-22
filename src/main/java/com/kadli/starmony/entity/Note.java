package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadli.starmony.interfaces.MusicalElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "note")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note implements MusicalElement {

    @Id
    @Column(name = "id_note")
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "symbol")
    protected String symbol;

    @Transient
    protected final String type = "note";

    @JsonIgnore
    @OneToMany(mappedBy = "interval_notes")
    protected List<ConcreteInterval> concreteIntervals;

    @JsonIgnore
    @OneToMany(mappedBy = "scale_note")
    protected List<ConcreteScale> concreteScales;

    @Override
    public String getType() { return type; }
}
