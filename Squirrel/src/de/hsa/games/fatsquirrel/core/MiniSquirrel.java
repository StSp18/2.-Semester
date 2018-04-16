package de.hsa.games.fatsquirrel.core;

public class MiniSquirrel extends Squirrel {
	private int mId;
	MiniSquirrel(int mId, int id, int energy, int x, int y) {
		super(id, energy, x, y);
		this.mId = mId;
	}
	
	public void nextStep() {
		xy = xy.rndDirection();
	}
	
	public int getMId() {
		return mId;
	}
	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
