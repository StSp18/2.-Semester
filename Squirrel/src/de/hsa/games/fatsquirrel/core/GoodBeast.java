package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XYsupport;

import java.util.logging.Logger;

public class GoodBeast extends Beast {
    private static Logger logger = Logger.getLogger("SquirrelLogger");

    public GoodBeast(int x, int y) {
        super(3, 200, x, y);
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


    public String toString() {
        return "Type: GOOD_BEAST, " + super.toString();
    }
}
