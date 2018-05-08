package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public abstract class MasterSquirrel extends Squirrel {
	int player;
	private MoveDirection moveDirection;
	
	protected MasterSquirrel(int id, int x, int y, int player) {
		super(id, 1000, x, y);
		this.player = player;
	}

	public int getPlayer() {
		return player;
	}
	
	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	public void nextStep(EntityContext context) {
		if (!Stunned()) {
			System.out.println("MasterSqirrel next Step");
			context.tryMove(this, moveDirection.getMoveDirection());
		}
	}

	public boolean myMiniSquirrel(Entity e) {
		if (e instanceof MiniSquirrel && getId() == ((MiniSquirrel) e).getMId()) {
			return true;
		}
		return false;
	}

	public MiniSquirrel creatMiniSquirrel(int id, int energy) throws NotEnoughEnergyException {
		if (energy <= 0) {
			throw new NotEnoughEnergyException("Can't create a MiniSquirrel with negative energy");
		}
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(this.getId(), id, energy, getX(), getY());
		}
		throw new NotEnoughEnergyException("I don't have the energy to do that");

	}

	public String toString() {
		return "Type: MasterSquirrel, " + super.toString();
	}
}
