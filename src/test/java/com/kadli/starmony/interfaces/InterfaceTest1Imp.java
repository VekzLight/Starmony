package com.kadli.starmony.interfaces;

public class InterfaceTest1Imp implements InterfaceTest1 {

    @Override
    public String getHola(String name) {
        return "Hola " + name;
    }
}
