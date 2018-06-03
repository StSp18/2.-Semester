package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XYsupport;

import java.util.logging.Logger;

public class BadBeast extends Beast {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    public BadBeast(int x, int y) {
        super(2, 1050, x, y);
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
            e.updateEnergy(-getEnergy());
            updateEnergy(-getEnergy());
        }
        return getEnergy() <= 0;
    }

    public String toString() {
        return "Type: BAD_BEAST, " + super.toString();
    }
}
