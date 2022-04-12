package com.kadli.starmony.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kadli.starmony.entity.Note;
import com.kadli.starmony.entity.Scale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcreteScaleDTO extends ScaleDTO implements Serializable {
    private NoteDTO tonic;
    private HashMap<NoteDTO,  Integer> notes;
}
