package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public abstract class MasterSquirrel extends Squirrel {
	private boolean spawn;
	private int spawnId, spawnEnergy;
	private int player;
	private MoveDirection moveDirection;
	
	protected MasterSquirrel(int id, int x, int y, int player) {
		super(id, 1000, x, y);
		this.player = player;
		spawn = false;
		moveDirection = MoveDirection.stay;
	}

	public int getPlayer() {
		return player;
	}
	
	public boolean getSpawn() {
		if(spawn == true) {
			spawn = false;
			return true;
		}
		return false;
		
	}
	
	public void setSpawn(int id, int energy) throws NotEnoughEnergyException {
		if(getEnergy() < energy) {
			throw new NotEnoughEnergyException("MasterSquirrel has not enough energy");
		}
		if(energy <= 0) {
			throw new NotEnoughEnergyException("Negative energy not allowed");
		}
		spawnId = id;
		spawnEnergy = energy;
		spawn = true;
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

	public MiniSquirrel creatMiniSquirrel (){
		this.updateEnergy(-spawnEnergy);
		return new MiniSquirrel(this.getId(), spawnId, spawnEnergy, getX(), getY());
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
