package de.hsa.games.fatsquirrel.core;

public class BadBeast extends Entity {

	BadBeast(int id, int x, int y) {
		super(id, -150, x, y);
	}
	
	public void nextStep() {
		xy = xy.rndDirection();
	}
	
	public boolean collision(Entity e) {
		return false;
	}
	
	public String toString() {
		return "Type: BadBeast, " + super.toString();
	}
}
