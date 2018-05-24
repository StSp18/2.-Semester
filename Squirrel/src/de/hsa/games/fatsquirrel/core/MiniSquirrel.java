package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

import java.util.logging.Logger;

public class MiniSquirrel extends Squirrel {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	private MasterSquirrel master;
	
	public MiniSquirrel(MasterSquirrel master, int energy, int x, int y) {
		super(6, energy, x, y);
		this.master = master;
		stunned = 1;
	}

	public MiniSquirrel createMiniSquirrel(int energy, XY direction) throws SpawnException {
		if (energy <= 0) {
			throw new SpawnException("Can't create a MINI_SQUIRREL with negative energy");
		}
		direction = direction.plus(xy);
		if (energy < this.getEnergy()) {
			this.updateEnergy(-energy);
			MiniSquirrel miniSquirrel = new MiniSquirrel(getMaster(), energy, direction.x, direction.y);
			logger.finer("Created: " + miniSquirrel.toString());
			return miniSquirrel;
		}
		throw new SpawnException("I don't have the energy to do that");

	}

	public void nextStep(EntityContext context) {
		if (!Stunned()) {
		    XY md = XYsupport.rndMoveDirection();
            logger.fine(this.getClass().getName() + " is moving: " + md.toString());
			context.tryMove(this, md);
		} else {
            logger.fine(this.getClass().getName() + " is stunned");
        }
	}
	
	public MasterSquirrel getMaster() {
	    return master;
    }

	public String toString() {
		return "Type: MINI_SQUIRREL, " + super.toString();
	}

}
