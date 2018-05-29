package de.hsa.games.fatsquirrel.core;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(6, -10, x, y);
    }

    public String toString() {
        return "Type: WALL, " + super.toString();
    }


}
