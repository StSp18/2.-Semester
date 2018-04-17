package de.hsa.games.fatsquirrel.core;

public abstract class Character extends Entity{
	XY mD;
	Character(int id, int energy, int x, int y) {
		super(id, energy, x, y);
	}

	public XY getMoveDirection() {
		return mD;
	}
	public abstract void nextStep(EntityContext context);
	
	public void setMoveDirection(XY mD) {
		this.mD = mD;
	}
	
	public String toString() {
		return super.toString();
	}
}
