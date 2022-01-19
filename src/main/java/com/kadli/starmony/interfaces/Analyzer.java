package com.kadli.starmony.interfaces;

import java.util.List;

public interface Analyzer {
    public List<MusicalElement> analyze(MusicalElement element);
    public List<MusicalElement> analyze(Integer elements);
}
