package it.giorgio.gol.model;

import it.giorgio.gol.exceptions.InvalidElementException;

import java.util.Arrays;

public enum Element {
    ALIVE('X'), DEAD(' ');
    private final char code;

    Element(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public boolean isAlive() {
        return this == ALIVE;
    }

    public static Element fromCode(char code) {
        return Arrays.stream(Element.values())
                .filter(it -> it.getCode() == code)
                .findAny()
                .orElseThrow(() -> new InvalidElementException(code));
    }

}
