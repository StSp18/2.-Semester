package de.hsa.games.fatsquirrel.core;

public class GoodBeast extends Character {
	private int sleep;
	private boolean alive;
	GoodBeast(int id, int x, int y) {
		super(id, 200, x, y);
		alive = true;
		sleep = 0;
	}

	public void nextStep(EntityContext context) {
		if(sleep == 0) {
			xy = xy.rndDirection();
			sleep = 3;
		} else {
			sleep--;
		}
	}
	
	public boolean collision(Entity e) {
		if(e instanceof Squirrel) {
			e.updateEnergy(getEnergy());
			return false;
		}
		return true;
	}
	
	
	public void kill() {
		alive = false;
	}
	
	public boolean alive() {
		return alive;
	}
	
	public String toString() {
		return "Type: GoodBeast, " + super.toString();
	}
}
