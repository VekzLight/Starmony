package com.kadli.starmony.dto;

import com.kadli.starmony.entity.Chord;
import com.kadli.starmony.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChordDTO {
    private Long id;
    private String name;
    private String symbol;
    private String code;
    private Note tonic;
    private List<Chord> notes;
}
