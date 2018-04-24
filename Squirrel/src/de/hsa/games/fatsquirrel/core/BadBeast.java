package de.hsa.games.fatsquirrel.core;

public class BadBeast extends Character {
	int lifes;
	private int sleep;
	BadBeast(int id, int x, int y) {
		super(id, -150, x, y);
		lifes = 7;
		sleep = 0;
	}
	
	public void nextStep(EntityContext context) {
		if(!aSleep()) {
			if(context.nearestPlayerEntity(getXY()) != null) {
				context.tryMove(this, context.moveTowards(this, context.nearestPlayerEntity(getXY())).getMoveDirection());
			}
			context.tryMove(this, context.rndMoveDirection().getMoveDirection());
		} else {
			System.out.println("BadBeast is asleep");
		}
	}
	
	public boolean bite(Entity e) {
		System.out.println("Bite");
		e.updateEnergy(getEnergy());
		lifes--;
		return lifes == 0;
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
		return "Type: BadBeast, " + super.toString();
	}
}
