package de.hsa.games.fatsquirrel.core;

public abstract class Squirrel extends Character {
	private int stunned;
	
	protected Squirrel(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		stunned  = 0;
	}
	
	protected boolean Stunned() {
		if(stunned > 0) {
			System.out.println("Stunned for :" + stunned);
			stunned--;
			return true;
		}
		return false;
	}
	
	public void wallBump() {
		System.out.println("You got stunned");
		stunned = 3;
	}
	
	public String toString() {
		return super.toString();
	}
}
