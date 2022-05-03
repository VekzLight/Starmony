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
@Table(name = "progression")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Progression implements MusicalElement {

    @Id
    @GeneratedValue
    @Column(name = "id_progression")
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "symbol")
    protected String symbol;

    @Column(name = "code")
    protected String code;

    @Transient
    protected final String type = "progression";

    @Override
    public String getType() { return type; }
}
