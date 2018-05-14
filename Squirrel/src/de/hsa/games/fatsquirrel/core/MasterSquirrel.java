package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public class MasterSquirrel extends Squirrel {
	private boolean spawn;
	private int spawnId, spawnEnergy;

	
	protected MasterSquirrel(int id, int x, int y) {
		super(id, 1000, x, y);
		spawn = false;
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
	

	
	public void nextStep(EntityContext context) {
		if (!Stunned()) {
//			System.out.println("MasterSqirrel next Step");
			context.tryMove(this, moveDirection.getMoveDirection());
		}
	}

	public boolean myMiniSquirrel(Entity e) {
		if (e instanceof MiniSquirrel && getId() == ((MiniSquirrel) e).getMId()) {
			return true;
		}
		return false;
	}

	public MiniSquirrel createMiniSquirrel(int id, int energy, XY direction) throws NotEnoughEnergyException {
		if (energy <= 0) {
			throw new NotEnoughEnergyException("Can't create a MiniSquirrel with negative energy");
		}
		direction = direction.add(getXY());
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(getId(), id, energy, direction.getX(), direction.getY());
		}
		throw new NotEnoughEnergyException("I don't have the energy to do that");

	}

	public MiniSquirrel createMiniSquirrel(){
		this.updateEnergy(-spawnEnergy);
		return new MiniSquirrel(this.getId(), spawnId, spawnEnergy, getX(), getY());
	}
	
	public MiniSquirrel createMiniSquirrel(int id, int energy) throws NotEnoughEnergyException {
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
