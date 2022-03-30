package com.kadli.starmony.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteScaleDTO extends ScaleDTO implements Serializable {
    private NoteDTO tonic;
    private List<NoteDTO> notes;
}
