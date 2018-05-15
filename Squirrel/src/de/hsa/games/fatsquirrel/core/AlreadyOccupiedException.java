package de.hsa.games.fatsquirrel.core;

public class AlreadyOccupiedException extends RuntimeException {
    public AlreadyOccupiedException(String msg) {
        super(msg);
    }
}
