package com.kadli.starmony.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadli.starmony.interfaces.MusicalElement;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "scale")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scale implements MusicalElement {

    @Id
    @Column(name = "id_scale")
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "symbol")
    protected String symbol;

    @Column(name = "code")
    protected String code;

    @Transient
    protected final String type = "scale";

    @JsonIgnore
    @OneToMany(mappedBy = "chord")
    protected List<Scale_Grades> scale_grades;

    @Override
    public String getType() { return type; }

}
