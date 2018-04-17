package de.hsa.games.fatsquirrel.core;

public class BadPlant extends Entity {

	BadPlant(int id, int x, int y) {
		super(id, -100, x, y);
	}

	public boolean collision(Entity e) {
		if(e instanceof Squirrel) {
			System.out.println("BadPlant got eaten");
			e.updateEnergy(getEnergy());
			return false;
		}
		return true;
	}
	
	public String toString() {
		return "Type: BadPlant, " + super.toString();
	}

}
