package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

import java.util.logging.Logger;

public class BadBeast extends Character {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private int sleep;
    public BadBeast(int x, int y) {
        super(4, 1050, x, y);
        sleep = 0;
    }

    public void nextStep(EntityContext context) {
        if(!aSleep()) {
            XY md;
            if(context.nearestPlayerEntity(xy) == null) {
                md = XYsupport.rndMoveDirection();
            } else {
                md = XYsupport.moveTowards(xy, context.nearestPlayerEntity(xy).xy);
            }
            context.tryMove(this, md);
            logger.fine(this.getClass().getName() + " is moving: " + md.toString());
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
