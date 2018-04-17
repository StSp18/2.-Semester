package de.hsa.games.fatsquirrel.core;

public class MiniSquirrel extends Squirrel {
	private int mId;

	MiniSquirrel(int mId, int id, int energy, int x, int y) {
		super(id, energy, x, y);
		this.mId = mId;
	}

	public boolean collision(Entity e) {
		if (e instanceof MasterSquirrel) {
			if (((MasterSquirrel) e).myMiniSquirrel(this)) {
				e.updateEnergy(getEnergy()); // unite
			}
			this.updateEnergy(-getEnergy());// die
		} else if (e instanceof MiniSquirrel) {
			if (((MiniSquirrel) e).getMId() == getMId()) {
				return true; // do nothing
			} else {
				this.updateEnergy(-getEnergy());// just die
			}
		} else if (e instanceof BadBeast) {
			((BadBeast) e).bite(this);
			if (getEnergy() > 0) {
				return true;
			}
		} else if(e instanceof GoodBeast) {
			updateEnergy(e.getEnergy());
			((GoodBeast) e).kill();
		}
		return false;
	}

	public void nextStep(EntityContext context) {
		xy = xy.move(getMoveDirection());
		this.updateEnergy(-1);
		if(getEnergy()<0) {
			context.kill(this);
		}
	}

	public int getMId() {
		return mId;
	}

	public String toString() {
		return "Type: MiniSquirrel, " + super.toString();
	}

}
