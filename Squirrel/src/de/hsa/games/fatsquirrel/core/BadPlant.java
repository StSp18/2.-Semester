package de.hsa.games.fatsquirrel.core;

public class BadPlant extends Entity {

	public BadPlant(int id, int x, int y) {
		super(id, -100, x, y);
	}

	public String toString() {
		return "Type: BadPlant, " + super.toString();
	}

}
