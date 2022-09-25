package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tag_progression")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagProgression {

    @EmbeddedId
    private TagProgressionId id = new TagProgressionId();

    @ManyToOne
    @JoinColumn(name = "tag_id_tag", updatable = false, insertable = false)
    private Tag tagOfProgression;

    @ManyToOne
    @JoinColumn(name = "progression_id_progression", updatable = false, insertable = false)
    private Progression progressionOfTag;

}
