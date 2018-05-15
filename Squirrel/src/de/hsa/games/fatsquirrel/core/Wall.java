package de.hsa.games.fatsquirrel.core;

public class Wall extends Entity {

	public Wall(int x, int y) {
		super(0, -10, x, y);
	}
	
	public String toString() {
		return "Type: Wall, " + super.toString();
	}


}
