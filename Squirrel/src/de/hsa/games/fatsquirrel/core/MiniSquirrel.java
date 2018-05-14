package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public class MiniSquirrel extends Squirrel {
	private int mId;
	
	public MiniSquirrel(int mId, int id, int energy, int x, int y) {
		super(id, energy, x, y);
		this.mId = mId;
	}

	public MiniSquirrel createMiniSquirrel(int id, int energy, XY direction) throws NotEnoughEnergyException {
		if (energy <= 0) {
			throw new NotEnoughEnergyException("Can't create a MiniSquirrel with negative energy");
		}
		direction = direction.add(getXY());
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(getMId(), id, energy, direction.getX(), direction.getY());
		}
		throw new NotEnoughEnergyException("I don't have the energy to do that");

	}

	public void nextStep(EntityContext context) {
		if (!Stunned()) {
//			System.out.println("MiniSqirrel next Step");
			context.tryMove(this, MoveDirection.rndMoveDirection().getMoveDirection());
		}
	}
	
	public int getMId() {
		return mId;
	}

	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
