package com.kadli.starmony.models;

import com.kadli.starmony.interfaces.MusicalElement;

public class Interval implements MusicalElement {
    protected String name;
    protected String symbol;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
