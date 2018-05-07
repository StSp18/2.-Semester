package de.hsa.games.fatsquirrel.core;

public class MiniSquirrel extends Squirrel {
	private int mId;
	
	public MiniSquirrel(int mId, int id, int energy, int x, int y) {
		super(id, energy, x, y, 1);
		this.mId = mId;
	}

	public void nextStep(EntityContext context) {
		if (!Stunned()) {
			System.out.println("MiniSqirrel next Step");
			context.tryMove(this, context.rndMoveDirection().getMoveDirection());
		}
	}

	
	
	public int getMId() {
		return mId;
	}

	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
