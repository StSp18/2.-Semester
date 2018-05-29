package de.hsa.games.fatsquirrel.core;

public class GoodPlant extends Entity {

    public GoodPlant(int x, int y) {
        super(1, 100, x, y);
    }

    public String toString() {
        return "Type: GOOD_PLANT, " + super.toString();
    }

}
