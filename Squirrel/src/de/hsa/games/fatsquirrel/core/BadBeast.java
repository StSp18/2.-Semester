package de.hsa.games.fatsquirrel.core;

public class BadBeast extends Character {
	int lifes;
	BadBeast(int id, int x, int y) {
		super(id, -150, x, y);
		lifes = 7;
	}
	
	public void nextStep(EntityContext context) {
		System.out.println("BadBeast next Step");
		context.tryMove(this, moveDirection);
	}
	
	public boolean bite(Entity e) {
		e.updateEnergy(getEnergy());
		lifes--;
		return lifes == 0;
	}
	
	public String toString() {
		return "Type: BadBeast, " + super.toString();
	}
}
