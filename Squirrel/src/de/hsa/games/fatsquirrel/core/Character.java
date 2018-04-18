package de.hsa.games.fatsquirrel.core;

public abstract class Character extends Entity{
	protected XY moveDirection;
	Character(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		moveDirection = new XY(0,0);
	}

	public abstract void nextStep(EntityContext context);
	
	public void move(XY moveDirection) {
		xy = xy.move(moveDirection);
	}
	
	public void setMoveDirection(XY moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	public String toString() {
		return super.toString();
	}
}
