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
public class TagProgressionId implements Serializable {

    @Column(name = "tag_id_tag")
    private Long id_tag;

    @Column(name = "progression_id_progression")
    private Long id_progression;

}
