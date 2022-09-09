package com.kadli.starmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tag_scale")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagScale {

    @EmbeddedId
    private TagScaleId id = new TagScaleId();

    @ManyToOne
    @JoinColumn(name = "tag_id_tag", updatable = false, insertable = false)
    private Tag tagOfScale;

    @ManyToOne
    @JoinColumn(name = "scale_id_scale", updatable = false, insertable = false)
    private Scale scaleOfTag;

}
