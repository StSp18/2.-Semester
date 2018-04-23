package de.hsa.games.fatsquirrel.core;

public abstract class Character extends Entity{
	protected MoveDirection moveDirection;
	Character(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		moveDirection = MoveDirection.stay;
	}

	public abstract void nextStep(EntityContext context);
	
	public void move() {
		System.out.println("Before move: " + xy.toString());
		System.out.println("move: " + moveDirection.toString());
		xy = xy.move(moveDirection.getMoveDirection());
		System.out.println("After move: " + xy.toString());
	}
	
	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	public String toString() {
		return super.toString();
	}
}
