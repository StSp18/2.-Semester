package de.hsa.games.fatsquirrel.core;

public class GoodBeast extends Character {
	private int sleep;
	GoodBeast(int id, int x, int y) {
		super(id, 200, x, y);
		sleep = 0;
	}

	public void nextStep(EntityContext context) {
		if(!aSleep()) {
			context.tryMove(this, context.planNextMove(getX(), getY()));
		} else {
			System.out.println("GoodBeast is asleep");
		}
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
		return "Type: GoodBeast, " + super.toString();
	}
}
