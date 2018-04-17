package de.hsa.games.fatsquirrel.core;

public class Wall extends Entity {

	Wall(int id, int x, int y) {
		super(id, -10, x, y);
	}
	
	public boolean collision(Entity e) {
		if(e instanceof Squirrel) {
			((Squirrel) e).wallBump();
		}
		return true;
	}
	
	public String toString() {
		return "Type: Wall, " + super.toString();
	}


}
