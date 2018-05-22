package de.hsa.games.fatsquirrel.core;

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
			MoveDirection md;
			if(context.nearestPlayerEntity(getXY()) == null) {
				md = MoveDirection.rndMoveDirection();
			} else {
				md = MoveDirection.moveAway(getXY(), context.nearestPlayerEntity(getXY()).getXY());
			}
			context.tryMove(this, md.getXY());
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
