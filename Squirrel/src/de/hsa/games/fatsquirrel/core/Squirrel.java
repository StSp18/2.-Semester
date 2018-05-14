package de.hsa.games.fatsquirrel.core;

public abstract class Squirrel extends Character {
	private int stunned;
	protected MoveDirection moveDirection;

	protected Squirrel(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		stunned  = 0;
		moveDirection = MoveDirection.stay;
	}

	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}
	public MoveDirection getMoveDirection() {
		return moveDirection;
	}

	protected boolean Stunned() {
		if(stunned > 0) {
//			System.out.println("Stunned for :" + stunned);
			stunned--;
			return true;
		}
		return false;
	}
	
	public void wallBump() {
//		System.out.println("You got stunned");
		stunned = 3;
	}
	
	public String toString() {
		return super.toString();
	}
}
