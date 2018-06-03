package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

import java.util.logging.Logger;

public class BadBeast extends Character {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private int sleep;
    public BadBeast(int x, int y) {
        super(2, 1050, x, y);
        sleep = 0;
        moveDirection = XYsupport.rndMoveDirection();
    }

    public void nextStep(EntityContext context) {
        if(!aSleep()) {
            Entity squirrel = context.nearestPlayerEntity(xy);
            if (squirrel != null) {
                moveDirection = XYsupport.moveTowards(xy, squirrel.xy);
            }
            context.tryMove(this, moveDirection);
            if (squirrel == null) {
                moveDirection = XYsupport.rndMoveDirection();
            }
            logger.fine(this.getClass().getName() + " is moving: " + moveDirection.toString());
        } else {
            logger.fine(this.getClass().getName() + " is asleep");
        }

    }

    public boolean bite(Entity e) {
        final int strength = 150;
        if(getEnergy() > strength) {
            updateEnergy(-strength);
            e.updateEnergy(-strength);
        } else {
            updateEnergy(-getEnergy());
            e.updateEnergy(-getEnergy());
        }
        return getEnergy() <= 0;
    }

    public boolean aSleep() {
        if(sleep == 0) {
            sleep = 3;
            return false;
        } else {
            sleep--;
            return true;
        }
    }

    public String toString() {
        return "Type: BAD_BEAST, " + super.toString();
    }
}
