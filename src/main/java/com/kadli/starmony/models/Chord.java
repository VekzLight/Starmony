package com.kadli.starmony.models;

import com.kadli.starmony.interfaces.MusicalElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chord")
@ToString @EqualsAndHashCode
public class Chord implements MusicalElement {

    @Id
    @Column(name = "id_chord")
    @Getter @Setter
    protected int id;

    @Column(name = "name")
    @Getter @Setter
    protected String name;

    @Column(name = "symbol")
    @Getter @Setter
    protected String symbol;

    @Column(name = "code")
    @Getter @Setter
    protected String code;
}