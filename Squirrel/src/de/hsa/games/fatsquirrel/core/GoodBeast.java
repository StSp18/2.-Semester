package de.hsa.games.fatsquirrel.core;

public class GoodBeast extends Character {
	private int sleep;
	public GoodBeast(int id, int x, int y) {
		super(id, 200, x, y);
		sleep = 0;
	}

	public void nextStep(EntityContext context) {
		if(!aSleep()) {
			if(context.nearestPlayerEntity(getXY()) != null) {
				context.tryMove(this, context.moveAway(this, context.nearestPlayerEntity(getXY())).getMoveDirection());
			}
			context.tryMove(this, context.rndMoveDirection().getMoveDirection());
		} else {
			System.out.println("GoodBeast is asleep");
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
		return "Type: GoodBeast, " + super.toString();
	}
}
