package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

import de.hsa.games.fatsquirrel.MoveCommand;

public class FlattenedBoard implements BoardView, EntityContext {
	private Board b;
	private Entity[][] fb;

	public FlattenedBoard(Board b) {
		this.b = b;
		fb = b.flatten();
	}

	public Entity[][] flatten() {
		return b.flatten();
	}

	public EntityType getEntityType(XY xy) {
		Entity e = fb[xy.getX()][xy.getY()];
		if (e instanceof BadPlant) {
			return EntityType.BadPlant;
		} else if (e instanceof GoodPlant) {
			return EntityType.GoodPlant;
		} else if (e instanceof BadBeast) {
			return EntityType.BadBeast;
		} else if (e instanceof GoodBeast) {
			return EntityType.GoodBeast;
		} else if (e instanceof MasterSquirrel) {
			return EntityType.MasterSquirrel;
		} else if (e instanceof MiniSquirrel) {
			return EntityType.MiniSquirrel;
		} else {
			return EntityType.Wall;
		}
	}

	public HandOperatedMasterSquirrel[] process() {
		HandOperatedMasterSquirrel[] sA = new HandOperatedMasterSquirrel[b.userControlled()];
		int count = 0;
		for (int i = 0; i < fb[0].length; i++) {
			for (int k = 0; k < fb[1].length; k++) {
				if (fb[i][k] instanceof BadBeast) {
					Squirrel s;
					if ((s = nearestPlayerEntity(new XY(i, k))) != null)
						tryMove((BadBeast) fb[i][k], moveTowards(fb[i][k], s));
					else
						tryMove((BadBeast) fb[i][k], (new XY(0, 0).rndDirection()));
				}
				if (fb[i][k] instanceof GoodBeast) {
					Squirrel s;
					if ((s = nearestPlayerEntity(new XY(i, k))) != null)
						tryMove((GoodBeast) fb[i][k], moveAway(fb[i][k], s));
					else
						tryMove((GoodBeast) fb[i][k], (new XY(0, 0).rndDirection()));
				}
				if (fb[i][k] instanceof HandOperatedMasterSquirrel) {
					sA[count] = (HandOperatedMasterSquirrel) fb[i][k];
					count++;
				}
			}
		}
		return sA;
	}

	public XY moveAway(Entity e, Entity s) {
		XY xy = moveAway(e, s);
		return new XY(-xy.getX(), -xy.getY());
	}

	public XY moveTowards(Entity e, Entity s) {
		int x = 0;
		int y = 0;
		if (s.getX() < e.getX())
			x = -1;
		if (s.getX() > e.getX())
			x = 1;
		if (s.getY() < e.getY())
			y = -1;
		if (s.getY() > e.getY())
			y = 1;
		return new XY(x, y);

	}

	public EntityType getEntityType(int x, int y) {
		Entity e = fb[x][y];
		if (e instanceof BadPlant) {
			return EntityType.BadPlant;
		} else if (e instanceof GoodPlant) {
			return EntityType.GoodPlant;
		} else if (e instanceof BadBeast) {
			return EntityType.BadBeast;
		} else if (e instanceof GoodBeast) {
			return EntityType.GoodBeast;
		} else if (e instanceof MasterSquirrel) {
			return EntityType.MasterSquirrel;
		} else if (e instanceof MiniSquirrel) {
			return EntityType.MiniSquirrel;
		} else if (e instanceof Wall) {
			return EntityType.Wall;
		} else {
			return EntityType.Air;
		}
	}

	public XY getSize() {
		return b.getSize();
	}

	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
		int x = miniSquirrel.getX() + moveDirection.getX();
		int y = miniSquirrel.getX() + moveDirection.getX();
		if (getEntityType(x, y) != EntityType.Air) {
			if (fb[x][y].collision(miniSquirrel)) {
				if (miniSquirrel.getEnergy() < 0) {
					kill(miniSquirrel);
				}
				miniSquirrel.setMoveDirection(new XY(0, 0));
				return;
			}
		}
		miniSquirrel.setMoveDirection(moveDirection);

	}

	public void tryMove(GoodBeast goodBeast, XY moveDirection) {
		int x = goodBeast.getX() + moveDirection.getX();
		int y = goodBeast.getX() + moveDirection.getX();
		if (getEntityType(x, y) != EntityType.Air) {
			if (fb[x][y].collision(goodBeast)) {
				if (goodBeast.alive()) {
					goodBeast.setMoveDirection(new XY(0, 0));
				} else {
					killAndReplace(goodBeast);
				}
			}
		}
		goodBeast.setMoveDirection(moveDirection);
	}

	public void tryMove(BadBeast badBeast, XY moveDirection) {
		int x = badBeast.getX() + moveDirection.getX();
		int y = badBeast.getX() + moveDirection.getX();
		if (getEntityType(x, y) != EntityType.Air) {
			if (fb[x][y].collision(badBeast)) {
				if (badBeast.alive()) {
					badBeast.setMoveDirection(new XY(0, 0));
				} else {
					killAndReplace(badBeast);
				}
			}
		}
		badBeast.setMoveDirection(moveDirection);
	}

	public void tryMove(MasterSquirrel master, XY moveDirection) {
		int x = master.getX() + moveDirection.getX();
		int y = master.getX() + moveDirection.getX();
		if (getEntityType(x, y) != EntityType.Air) {
			if (fb[x][y].collision(master)) {
				master.setMoveDirection(new XY(0, 0));
				return;
			}
		}
		master.setMoveDirection(moveDirection);
	}

	public Squirrel nearestPlayerEntity(XY pos) {
		boolean right = true;
		boolean left = true;
		boolean up = true;
		boolean down = true;
		for (int i = 0; i <= 6; i++) {
			if(pos.getX()+i > fb[0].length-1)
				right = false;
			if(pos.getX()-i < 0)
				left = false;
			if(pos.getY()+i > fb[1].length-1)
				down = false;
			if(pos.getY()-i < 0)
				up = false;
			// oben
			if (up && fb[pos.getX()][pos.getY() - i] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX()][pos.getY() - i];
				}
			// unten
			if (down && fb[pos.getX()][pos.getY() + i] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX()][pos.getY() + i];
				}
			// rechts
			if (right && fb[pos.getX() + i][pos.getY()] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX() + i][pos.getY()];
				}
			// links
			if (left && fb[pos.getX() - i][pos.getY()] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX() - i][pos.getY()];
				}
			// obenrechts
			if (right && up && fb[pos.getX() + i][pos.getY() - i] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX() + i][pos.getY() - i];
				}
			// obenlinks
			if (left && up && fb[pos.getX() - i][pos.getY() - i] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX() - i][pos.getY() - i];
				}
			// untenrechts
			if (right && down && fb[pos.getX() + i][pos.getY() + i] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX() + i][pos.getY() + i];
				}
			// untenlinks
			if (left && down && fb[pos.getX() - i][pos.getY() + i] instanceof Squirrel) {
					return (Squirrel) fb[pos.getX() - i][pos.getY() + i];
				}
		}
		return null;
	}

	public void kill(Entity e) {
		b.remove(e);
	}

	public void killAndReplace(Entity e) {
		int rndX;
		int rndY;
		do {
			rndX = ThreadLocalRandom.current().nextInt(1, getSize().getX() + 1);
			rndY = ThreadLocalRandom.current().nextInt(1, getSize().getX() + 1);
		} while (!(getEntityType(rndX, rndY) == EntityType.Air));
		if (e instanceof BadBeast) {

		} else if (e instanceof GoodBeast) {

		} else if (e instanceof GoodPlant) {

		} else if (e instanceof BadPlant) {

		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < fb[0].length; i++) {
			for (int k = 0; k < fb[1].length; k++) {
				if (fb[i][k] instanceof BadPlant) {
					s += '-';
				} else if (fb[i][k] instanceof GoodPlant) {
					s += '+';
				} else if (fb[i][k] instanceof BadBeast) {
					s += 'B';
				} else if (fb[i][k] instanceof GoodBeast) {
					s += 'G';
				} else if (fb[i][k] instanceof MasterSquirrel) {
					s += 'O';
				} else if (fb[i][k] instanceof MiniSquirrel) {
					s += 'o';
				} else if (fb[i][k] instanceof Wall) {
					s += '|';
				} else {
					s += ' ';
				}
			}
			s += '\n';
		}
		return s;
	}

}
