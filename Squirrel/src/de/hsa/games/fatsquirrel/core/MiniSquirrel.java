package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public class MiniSquirrel extends Squirrel {
	private MasterSquirrel master;
	
	public MiniSquirrel(MasterSquirrel master, int energy, int x, int y) {
		super(6, energy, x, y);
		this.master = master;
	}

	public MiniSquirrel createMiniSquirrel(int energy, XY direction) throws NotEnoughEnergyException {
		if (energy <= 0) {
			throw new NotEnoughEnergyException("Can't create a MiniSquirrel with negative energy");
		}
		direction = direction.add(getXY());
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(getMaster(), energy, direction.getX(), direction.getY());
		}
		throw new NotEnoughEnergyException("I don't have the energy to do that");

	}

	public void nextStep(EntityContext context) {
		if (!Stunned()) {
//			System.out.println("MiniSqirrel next Step");
			context.tryMove(this, MoveDirection.rndMoveDirection().getXY());
		}
	}
	
	public MasterSquirrel getMaster() {
	    return master;
    }
	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
