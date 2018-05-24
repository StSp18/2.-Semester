package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;

import java.util.logging.Logger;

public abstract class MasterSquirrel extends Squirrel {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	
	protected MasterSquirrel(int x, int y) {
		super(5, 1000, x, y);
	}

	public void nextStep(EntityContext context) {
		if (!Stunned()) {
			logger.fine(this.getClass().getName() + " is moving: " + moveDirection.toString());
			context.tryMove(this, moveDirection);
		} else {
			logger.fine(this.getClass().getName() + " is stunned");
		}
	}

	public boolean myMiniSquirrel(Entity e) {
		if (e instanceof MiniSquirrel && this == ((MiniSquirrel) e).getMaster()) {
			return true;
		}
		return false;
	}

	public MiniSquirrel createMiniSquirrel(int energy, XY direction) throws SpawnException {
		if (energy <= 0) {
			throw new SpawnException("Can't create a MINI_SQUIRREL with negative energy");
		}
		direction = direction.plus(xy);
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			MiniSquirrel miniSquirrel = new MiniSquirrel(this, energy, direction.x, direction.y);
            logger.finer("Created: " + miniSquirrel.toString());
			return miniSquirrel;
		}
		throw new SpawnException("I don't have the energy to do that");

	}

	public String toString() {
		return super.toString();
	}
}
