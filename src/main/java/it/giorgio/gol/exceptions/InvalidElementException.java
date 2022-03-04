package it.giorgio.gol.exceptions;

public class InvalidElementException  extends GameOfLiveException {

    public InvalidElementException(char code) {
        super("Invalid code: " + code);
    }
}
