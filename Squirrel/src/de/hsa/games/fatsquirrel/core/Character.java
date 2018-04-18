package de.hsa.games.fatsquirrel.core;

public abstract class Character extends Entity{
	protected XY moveDirection;
	Character(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		moveDirection = new XY(0,0);
	}

	public abstract void nextStep(EntityContext context);
	
	public void move() {
		System.out.println("Before move: " + xy.toString());
		System.out.println("move: " + moveDirection.toString());
		xy = xy.move(moveDirection);
		System.out.println("After move: " + xy.toString());
	}
	
	public void setMoveDirection(XY moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	public String toString() {
		return super.toString();
	}
}
