package de.hsa.games.fatsquirrel.core;

public class BadPlant extends Entity {

    public BadPlant( int x, int y) {
        super(0, -100, x, y);
    }

    public String toString() {
        return "Type: BAD_PLANT, " + super.toString();
    }

}
