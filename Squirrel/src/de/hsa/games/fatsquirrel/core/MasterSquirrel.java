package de.hsa.games.fatsquirrel.core;

public abstract class MasterSquirrel extends Squirrel {

	MasterSquirrel(int id, int x, int y) {
		super(id, 1000, x, y);
	}
	
	public void nextStep(EntityContext context) {
		if(getEnergy()<0) {
			updateEnergy(-getEnergy());
		}
		System.out.println("Master Squirrel nextStep, " + getMoveDirection().toString());
		System.out.println("Before Move: " + toString());
		xy = xy.move(getMoveDirection());
		System.out.println("After Move" + toString());
	}
	
	public boolean myMiniSquirrel(Entity e) {
		if(e instanceof MiniSquirrel && getId() == ((MiniSquirrel) e).getMId()) {
			return true;
		} 
		return false;
	}

	public boolean collision(Entity e) {
		if(e instanceof BadBeast) {
			updateEnergy(e.getEnergy());
			((BadBeast)e).bite(this);
		} else if(e instanceof MiniSquirrel) {
			if(myMiniSquirrel(e)) {
				updateEnergy(e.getEnergy());
			} else {
				updateEnergy(150);
			}
			e.updateEnergy(-e.getEnergy());
		} else if(e instanceof GoodBeast) {
			updateEnergy(e.getEnergy());
			((GoodBeast) e).kill();
		}
		return true;
	}
	
	public MiniSquirrel creatMiniSquirrel(int id, int energy) {
		if(energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			return new MiniSquirrel(this.getId(), id, energy, this.getX()+1, this.getY());
		} return null;
		
	}
	
	public String toString() {
		return "Type: MasterSquirrel, " + super.toString();
	}
}
