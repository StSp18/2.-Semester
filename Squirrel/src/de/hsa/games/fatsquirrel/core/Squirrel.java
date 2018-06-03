package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

public abstract class Squirrel extends Character {
    protected int stunned;

    protected Squirrel(int id, int energy, int x, int y) {
        super(id, energy, x, y);
        stunned  = 0;
        moveDirection = XY.ZERO_ZERO;
    }

    public void setMoveDirection(XY moveDirection) {
        if(XYsupport.isDirection(moveDirection)){
            this.moveDirection = moveDirection;
        } else {
            this.moveDirection = XY.ZERO_ZERO;
        }

    }

    public boolean Stunned() {
        if(stunned > 0) {
            stunned--;
            return true;
        }
        return false;
    }

    public void wallBump() {
        stunned = 3;
    }

    public String toString() {
        return super.toString();
    }
}
