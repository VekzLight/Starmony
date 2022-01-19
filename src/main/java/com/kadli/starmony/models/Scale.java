package com.kadli.starmony.models;

import com.kadli.starmony.interfaces.MusicalElement;
import lombok.Getter;
import lombok.Setter;

public class Scale implements MusicalElement {

    @Getter @Setter
    protected String name;
    @Getter @Setter
    protected String symbol;
    @Getter @Setter
    protected String code;

}
