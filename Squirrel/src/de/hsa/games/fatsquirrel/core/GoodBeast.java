package de.hsa.games.fatsquirrel.core;

public class GoodBeast extends Character {
	private int sleep;
	public GoodBeast(int id, int x, int y) {
		super(id, 200, x, y);
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
			context.tryMove(this, md.getMoveDirection());
		} else {
//			System.out.println("GoodBeast is asleep");
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
