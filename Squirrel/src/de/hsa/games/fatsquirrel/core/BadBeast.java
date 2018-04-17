package de.hsa.games.fatsquirrel.core;

public class BadBeast extends Character {
	int lifes;
	BadBeast(int id, int x, int y) {
		super(id, -150, x, y);
		lifes = 7;
	}
	
	public void nextStep(EntityContext context) {
		xy = xy.rndDirection();
	}
	
	public boolean collision(Entity e) {
		if(e instanceof Squirrel) {
			System.out.println("BadBeast bites");
			bite(e);
			if(lifes==0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean alive() {
		return lifes > 0;
	}
	
	public void bite(Entity e) {
		e.updateEnergy(getEnergy());
		lifes--;
	}
	
	public String toString() {
		return "Type: BadBeast, " + super.toString();
	}
}
