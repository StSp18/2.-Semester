package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

import java.util.logging.Logger;

public class GoodBeast extends Character {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	private int sleep;
	public GoodBeast(int x, int y) {
		super(1, 200, x, y);
		sleep = 0;
	}

	public void nextStep(EntityContext context) {
		if(!aSleep()) {
			XY md;
			if(context.nearestPlayerEntity(xy) == null) {
				md = XYsupport.rndMoveDirection();
			} else {
				md = XYsupport.moveAway(xy, context.nearestPlayerEntity(xy).xy);
			}
			context.tryMove(this, md);
            logger.fine(this.getClass().getName() + " is moving: " + md.toString());
		} else {
            logger.fine(this.getClass().getName() + " is asleep");
		}
	}
	
	private boolean aSleep() {
		if(sleep == 0) {
			sleep = 3;
			return false;
		} else {
			sleep--;
			return true;
		}
	}

	public String toString() {
		return "Type: GOOD_BEAST, " + super.toString();
	}
}
