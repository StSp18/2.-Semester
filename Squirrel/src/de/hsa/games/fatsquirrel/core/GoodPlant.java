package de.hsa.games.fatsquirrel.core;

public class GoodPlant extends Entity {

	GoodPlant(int id, int x, int y) {
		super(id, 100, x, y);
	}
	
	public boolean collision(Entity e) {
		if(e instanceof Squirrel) {
			e.updateEnergy(getEnergy());
			return false;
		}
		return true;
	}
	
	public String toString() {
		return "Type: GoodPlant, " + super.toString();
	}

}
