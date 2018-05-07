package de.hsa.games.fatsquirrel.core;

public abstract class Character extends Entity{
	
	protected Character(int id, int energy, int x, int y) {
		super(id, energy, x, y);
	}

	public abstract void nextStep(EntityContext context);
	
	public void move(XY moveDirection) {
		System.out.println("Before move: " + xy.toString());
		System.out.println("move: " + moveDirection.toString());
		xy = xy.move(moveDirection);
		System.out.println("After move: " + xy.toString());
	}
	
	public String toString() {
		return super.toString();
	}
}
