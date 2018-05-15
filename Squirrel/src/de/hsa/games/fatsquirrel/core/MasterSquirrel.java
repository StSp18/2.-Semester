package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public class MasterSquirrel extends Squirrel {
	
	protected MasterSquirrel(int x, int y) {
		super(5, 1000, x, y);
	}

	public void nextStep(EntityContext context) {
		if (!Stunned()) {
//			System.out.println("MasterSqirrel next Step");
			context.tryMove(this, moveDirection.getXY());
		}
		if(getEnergy() < 0) {
		    updateEnergy(-getEnergy());
        }
	}

	public boolean myMiniSquirrel(Entity e) {
		if (e instanceof MiniSquirrel && this == ((MiniSquirrel) e).getMaster()) {
			return true;
		}
		return false;
	}

	public MiniSquirrel createMiniSquirrel(int energy, XY direction) throws NotEnoughEnergyException {
		if (energy <= 0) {
			throw new NotEnoughEnergyException("Can't create a MiniSquirrel with negative energy");
		}
		direction = direction.add(getXY());
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(this, energy, direction.getX(), direction.getY());
		}
		throw new NotEnoughEnergyException("I don't have the energy to do that");

	}

	public String toString() {
		return "Type: MasterSquirrel, " + super.toString();
	}
}
