package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public class FlattenedBoard implements BoardView, EntityContext {
	private Board b;
	private Entity[][] fb;

	public FlattenedBoard(Board b) {
		this.b = b;
		fb = flatten();
	}

	public Entity[][] flatten() {
		return b.flatten();
	}

	private void update() {
		fb = flatten();
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
		} else if (e instanceof HandOperatedMasterSquirrel) {
			return EntityType.HandOperatedMasterSquirrel;
		} else if (e instanceof AutomatedMasterSquirrel) {
			return EntityType.AutomatedMasterSquirrel;
		} else if (e instanceof MiniSquirrel) {
			return EntityType.MiniSquirrel;
		} else if (e instanceof Wall) {
			return EntityType.Wall;
		} else {
			return EntityType.Air;
		}
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
		} else if (e instanceof HandOperatedMasterSquirrel) {
			return EntityType.HandOperatedMasterSquirrel;
		} else if (e instanceof AutomatedMasterSquirrel) {
			return EntityType.AutomatedMasterSquirrel;
		} else if (e instanceof MiniSquirrel) {
			return EntityType.MiniSquirrel;
		} else if (e instanceof Wall) {
			return EntityType.Wall;
		} else {
			return EntityType.Air;
		}
	}

	private XY rndMoveDirection() {
		return new XY(0, 0).rndDirection();
	}

	public XY moveAway(Entity e, Entity s) {
		int x = 0;
		int y = 0;
		if (s.getX() > e.getX()) {
			x = -1;
		}
		if (s.getX() < e.getX()) {
			x = 1;
		}
		if (s.getY() > e.getY()) {
			y = -1;
		}
		if (s.getY() < e.getY()) {
			y = 1;
		}
		return new XY(x, y);
	}

	public XY moveTowards(Entity e, Entity s) {
		int x = 0;
		int y = 0;
		if (s.getX() < e.getX()) {
			x = -1;
		}
		if (s.getX() > e.getX()) {
			x = 1;
		}
		if (s.getY() < e.getY()) {
			y = -1;
		}
		if (s.getY() > e.getY()) {
			y = 1;
		}
		return new XY(x, y);

	}

	public XY getSize() {
		return new XY(fb[0].length, fb[1].length);
	}

	public void tryMove(MasterSquirrel master, XY moveDirection) {
		System.out.println("tryMove MasterSquirrel");
		XY newCorrdinates = master.getXY().add(moveDirection);
		if(master.Stunned()) {
			return;
		}
		if (getEntityType(newCorrdinates) == EntityType.Air) {
			master.move();
		}
		if (getEntityType(newCorrdinates) == EntityType.Wall) {
			master.wallBump();
		}
		if (getEntityType(newCorrdinates) == EntityType.BadBeast) {
			if (((BadBeast) getEntity(newCorrdinates)).bite(master)) {
				master.move();
				killAndReplace(getEntity(newCorrdinates));
			}
		}
		if (getEntityType(newCorrdinates) == EntityType.GoodBeast) {
			master.updateEnergy(getEntity(newCorrdinates).getEnergy());
			master.move();
			killAndReplace(getEntity(newCorrdinates));
		}
		if (getEntityType(newCorrdinates) == EntityType.BadPlant) {
			master.updateEnergy(getEntity(newCorrdinates).getEnergy());
			master.move();
			killAndReplace(getEntity(newCorrdinates));
		}
		if (getEntityType(newCorrdinates) == EntityType.GoodPlant) {
			master.updateEnergy(getEntity(newCorrdinates).getEnergy());
			master.move();
			killAndReplace(getEntity(newCorrdinates));
		}
		if (getEntityType(newCorrdinates) == EntityType.MiniSquirrel) {
			if(master.myMiniSquirrel(getEntity(newCorrdinates))) {
				master.updateEnergy(getEntity(newCorrdinates).getEnergy());
			} else {
				master.updateEnergy(150);
			}
			master.move();
			kill(getEntity(newCorrdinates));
		}
		update();
	}

	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
		System.out.println("tryMove miniSquirrel");
		XY newCorrdinates = miniSquirrel.getXY().add(moveDirection);
		if (getEntityType(newCorrdinates) == EntityType.Air) {
			miniSquirrel.move();
		}
		if (getEntityType(newCorrdinates) == EntityType.Wall) {
			miniSquirrel.wallBump();
		}
		if (getEntityType(newCorrdinates) == EntityType.BadBeast) {
			if (((BadBeast) getEntity(newCorrdinates)).bite(miniSquirrel)) {
				miniSquirrel.move();
				killAndReplace(getEntity(newCorrdinates));
			}
		}
		if (getEntityType(newCorrdinates) == EntityType.GoodBeast) {
			miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
			miniSquirrel.move();
			killAndReplace(getEntity(newCorrdinates));
		}
		if (getEntityType(newCorrdinates) == EntityType.BadPlant) {
			miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
			miniSquirrel.move();
			killAndReplace(getEntity(newCorrdinates));
		}
		if (getEntityType(newCorrdinates) == EntityType.GoodPlant) {
			miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
			miniSquirrel.move();
			killAndReplace(getEntity(newCorrdinates));
		}
		if (getEntityType(newCorrdinates) == EntityType.MiniSquirrel) {
			if(miniSquirrel.getMId() != ((MiniSquirrel) getEntity(newCorrdinates)).getMId()) {
				kill(miniSquirrel);
			}
		}
		if (getEntityType(newCorrdinates) == EntityType.AutomatedMasterSquirrel || getEntityType(newCorrdinates) == EntityType.HandOperatedMasterSquirrel) {
			kill(miniSquirrel);
		}
		miniSquirrel.updateEnergy(-1);
		if(miniSquirrel.getEnergy() <= 0) {
			kill(miniSquirrel);
		}
		update();
	}

	public void tryMove(GoodBeast goodBeast, XY moveDirection) {
		System.out.println("tryMove goodBeast");
		XY newCorrdinates = goodBeast.getXY().add(moveDirection);
		if(getEntityType(newCorrdinates) == EntityType.Air) {
			goodBeast.move();
		}
		if(getEntity(newCorrdinates) instanceof Squirrel) {
			getEntity(newCorrdinates).updateEnergy(goodBeast.getEnergy());
			kill(goodBeast);
		}
		update();
	}

	public void tryMove(BadBeast badBeast, XY moveDirection) {
		System.out.println("tryMove badBeast");
		XY newCorrdinates = badBeast.getXY().add(moveDirection);
		System.out.println(getEntityType(newCorrdinates));
		if(getEntityType(newCorrdinates) == EntityType.Air) {
			badBeast.move();
			return;
		}
		if(getEntity(newCorrdinates) instanceof Squirrel) {
			if(badBeast.bite(getEntity(newCorrdinates))) {
				killAndReplace(badBeast);
				return;
			}
		}
		update();
	}

	public Squirrel nearestPlayerEntity(XY pos) {
		boolean right = true;
		boolean left = true;
		boolean up = true;
		boolean down = true;
		for (int i = 1; i <= 6; i++) {
			if (pos.getX() + i > fb[0].length - 1)
				right = false;
			if (pos.getX() - i < 0)
				left = false;
			if (pos.getY() + i > fb[1].length - 1)
				down = false;
			if (pos.getY() - i < 0)
				up = false;
			for (int k = 0; k <= i; k++) {
				// obenrechts
				if (right && up && fb[pos.getX() + k][pos.getY() - i] instanceof Squirrel) {
//					System.out.print("Found Squirrel upright, ");
					return (Squirrel) fb[pos.getX() + k][pos.getY() - i];
				}
				if (right && up && fb[pos.getX() + i][pos.getY() - k] instanceof Squirrel) {
//					System.out.print("Found Squirrel upright, ");
					return (Squirrel) fb[pos.getX() + i][pos.getY() - k];
				}
				// obenlinks
				if (left && up && fb[pos.getX() - i][pos.getY() - k] instanceof Squirrel) {
//					System.out.print("Found Squirrel upleft, ");
					return (Squirrel) fb[pos.getX() - i][pos.getY() - k];
				}
				if (left && up && fb[pos.getX() - k][pos.getY() - i] instanceof Squirrel) {
//					System.out.print("Found Squirrel upleft, ");
					return (Squirrel) fb[pos.getX() - k][pos.getY() - i];
				}
				// untenrechts
				if (right && down && fb[pos.getX() + k][pos.getY() + i] instanceof Squirrel) {
//					System.out.print("Found Squirrel downright, ");
					return (Squirrel) fb[pos.getX() + k][pos.getY() + i];
				}
				if (right && down && fb[pos.getX() + i][pos.getY() + k] instanceof Squirrel) {
//					System.out.print("Found Squirrel downright, ");
					return (Squirrel) fb[pos.getX() + i][pos.getY() + k];
				}
				// untenlinks
				if (left && down && fb[pos.getX() - i][pos.getY() + k] instanceof Squirrel) {
//					System.out.print("Found Squirrel downleft, ");
					return (Squirrel) fb[pos.getX() - i][pos.getY() + k];
				}
				if (left && down && fb[pos.getX() - k][pos.getY() + i] instanceof Squirrel) {
//					System.out.print("Found Squirrel downleft, ");
					return (Squirrel) fb[pos.getX() - k][pos.getY() + i];
				}
			}
		}
//		System.out.print("Found no Squirrel nearby, ");
		return null;
	}

	public void kill(Entity e) {
		System.out.println("kill " + getEntityType(e.getX(), e.getY()).getChar());
		b.remove(e);
	}

	public void killAndReplace(Entity e) {
		System.out.println("kill and replace " + getEntityType(e.getX(), e.getY()).getChar());
		int rndX;
		int rndY;
		do {
			rndX = ThreadLocalRandom.current().nextInt(1, fb[0].length);
			rndY = ThreadLocalRandom.current().nextInt(1, fb[1].length);
		} while (getEntityType(rndX, rndY) != EntityType.Air);
		if (e instanceof BadBeast) {
			b.relocate(e, new BadBeast(e.getId(), rndX, rndY));
			;
		} else if (e instanceof GoodBeast) {
			b.relocate(e, new GoodBeast(e.getId(), rndX, rndY));
			;
		} else if (e instanceof GoodPlant) {
			b.relocate(e, new GoodPlant(e.getId(), rndX, rndY));
			;
		} else if (e instanceof BadPlant) {
			b.relocate(e, new BadPlant(e.getId(), rndX, rndY));
			;
		}
	}

	private Entity getEntity(XY coordinates) {
		return fb[coordinates.getX()][coordinates.getY()];
	}

	public void planNextMove(int x, int y) {
		System.out.println("Plan next Move for a Beast");
		if (getEntityType(x, y) == EntityType.BadBeast) {
			if (nearestPlayerEntity(new XY(x, y)) != null) {
				((Character) fb[x][y]).setMoveDirection(moveTowards(fb[x][y], (nearestPlayerEntity(new XY(x, y)))));
			} else {
				((Character) fb[x][y]).setMoveDirection(rndMoveDirection());
			}
		}
		if (getEntityType(x, y) == EntityType.GoodBeast) {
			if (nearestPlayerEntity(new XY(x, y)) != null) {
				((Character) fb[x][y]).setMoveDirection(moveAway(fb[x][y], (nearestPlayerEntity(new XY(x, y)))));
			} else {
				((Character) fb[x][y]).setMoveDirection(rndMoveDirection());
			}
		}

	}

	public String toString() {
		String s = "";

		for (int k = 0; k < fb[1].length; k++) {
			for (int i = 0; i < fb[0].length; i++) {
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
