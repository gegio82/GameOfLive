package it.giorgio.gol.exceptions;

public abstract class GameOfLiveException extends RuntimeException {

    public GameOfLiveException() {
        super();
    }

    public GameOfLiveException(String message) {
        super(message);
    }
}
