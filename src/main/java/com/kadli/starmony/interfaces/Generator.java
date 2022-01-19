package com.kadli.starmony.interfaces;

import java.util.ArrayList;

public interface Generator {

    public ArrayList<MusicalElement> generate(MusicalElement element);
    public ArrayList<MusicalElement> generate(ArrayList<MusicalElement> elements);

}
