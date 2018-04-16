package de.hsa.games.fatsquirrel.core;

public class BadPlant extends Entity {

	BadPlant(int id, int x, int y) {
		super(id, -100, x, y);
	}

	public boolean collision(Entity e) {
		return false;
	}
	
	public void nextStep() {
	}
	
	public String toString() {
		return "Type: BadPlant, " + super.toString();
	}

}
