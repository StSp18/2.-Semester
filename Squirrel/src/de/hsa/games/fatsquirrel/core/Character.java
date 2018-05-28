package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

public abstract class Character extends Entity{

    protected Character(int id, int energy, int x, int y) {
        super(id, energy, x, y);
    }

    public abstract void nextStep(EntityContext context);

    public void move(XY moveDirection) {
        xy = xy.plus(moveDirection);
    }

    public String toString() {
        return super.toString();
    }
}
