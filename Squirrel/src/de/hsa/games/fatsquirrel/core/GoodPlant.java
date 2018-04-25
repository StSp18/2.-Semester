package de.hsa.games.fatsquirrel.core;

public class GoodPlant extends Entity {

	public GoodPlant(int id, int x, int y) {
		super(id, 100, x, y);
	}
	
	public String toString() {
		return "Type: GoodPlant, " + super.toString();
	}

}
