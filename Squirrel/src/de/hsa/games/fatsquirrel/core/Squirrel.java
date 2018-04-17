package de.hsa.games.fatsquirrel.core;

public abstract class Squirrel extends Character {
	private int stunned;
	
	Squirrel(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		stunned  = 0;
	}
	
	public boolean Stunned() {
		if(stunned != 0) {
			stunned--;
			return true;
		}
		return false;
	}
	
	public void wallBump() {
		stunned = 3;
	}
	
	public String toString() {
		return super.toString();
	}
}
