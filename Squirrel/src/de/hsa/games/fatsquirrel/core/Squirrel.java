package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

public abstract class Squirrel extends Character {
    protected int stunned;
    protected XY moveDirection;

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
    public XY getMoveDirection() {
        return moveDirection;
    }

    protected boolean Stunned() {
        if(stunned > 0) {
//            System.out.println("Stunned for :" + stunned);
            stunned--;
            return true;
        }
        return false;
    }

    public void wallBump() {
//        System.out.println("You got stunned");
        stunned = 3;
    }

    public String toString() {
        return super.toString();
    }
}
