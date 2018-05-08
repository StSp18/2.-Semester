package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public class FlattenedBoard implements BoardView, EntityContext {
	private Board b;
	private Entity[][] fb;
	
	public FlattenedBoard(Entity[][] fb, Board b) {
		this.fb = fb;
		this.b = b;
	}

	public Entity[][] getFlattenedBoard() {
		return fb;
	}

	public EntityType getEntityType(int x, int y) {
		return getEntityType(new XY(x, y));
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





	public XY getSize() {
		return new XY(fb[0].length, fb[1].length);
	}

	public void tryMove(MasterSquirrel master, XY moveDirection) {
		System.out.println("tryMove MasterSquirrel");
		XY newCorrdinates = master.getXY().add(moveDirection);
		if (!newCorrdinates.equals(master.getXY())) {
			switch (getEntityType(newCorrdinates)) {
			case Air:
				break;
			case BadBeast:
				if (((BadBeast) getEntity(newCorrdinates)).bite(master)) {
					killAndReplace(getEntity(newCorrdinates));
				} else {
					return;
				}
				break;
			case BadPlant:
				master.updateEnergy(getEntity(newCorrdinates).getEnergy());
				killAndReplace(getEntity(newCorrdinates));
				break;
			case GoodBeast:
				master.updateEnergy(getEntity(newCorrdinates).getEnergy());
				killAndReplace(getEntity(newCorrdinates));
				break;
			case GoodPlant:
				master.updateEnergy(getEntity(newCorrdinates).getEnergy());
				killAndReplace(getEntity(newCorrdinates));
				break;
			case HandOperatedMasterSquirrel:
			case AutomatedMasterSquirrel:
				return;
			case MiniSquirrel:
				if (master.myMiniSquirrel(getEntity(newCorrdinates))) {
					master.updateEnergy(getEntity(newCorrdinates).getEnergy());
				} else {
					master.updateEnergy(150);
				}
				kill(getEntity(newCorrdinates));
				break;
			case Wall:
				master.updateEnergy(getEntity(newCorrdinates).getEnergy());
				master.wallBump();
				return;
			default:
				break;
			}
			master.move(moveDirection);
		}
	}

	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
		System.out.println("tryMove miniSquirrel");
		XY newCorrdinates = miniSquirrel.getXY().add(moveDirection);
		if (!newCorrdinates.equals(miniSquirrel.getXY())) {
			switch (getEntityType(newCorrdinates)) {
			case Air:
				break;
			case BadBeast:
				if (((BadBeast) getEntity(newCorrdinates)).bite(miniSquirrel)) {
					killAndReplace(getEntity(newCorrdinates));
				} else {
					return;
				}
				break;
			case BadPlant:
				miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
				killAndReplace(getEntity(newCorrdinates));
				break;
			case GoodBeast:
				miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
				killAndReplace(getEntity(newCorrdinates));
				break;
			case GoodPlant:
				miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
				killAndReplace(getEntity(newCorrdinates));
				break;
			case AutomatedMasterSquirrel:
			case HandOperatedMasterSquirrel:
				if (miniSquirrel.getMId() == getEntity(newCorrdinates).getId()) {
					getEntity(newCorrdinates).updateEnergy(miniSquirrel.getEnergy());
				}
				kill(miniSquirrel);
				return;
			case MiniSquirrel:
				if (miniSquirrel.getMId() != ((MiniSquirrel) getEntity(newCorrdinates)).getMId()) {
					kill(miniSquirrel);
				}
				return;
			case Wall:
				miniSquirrel.updateEnergy(getEntity(newCorrdinates).getEnergy());
				miniSquirrel.wallBump();
				return;
			default:
				break;

			}
			miniSquirrel.updateEnergy(-1);
			if (miniSquirrel.getEnergy() <= 0) {
				kill(miniSquirrel);
			} else {
				miniSquirrel.move(moveDirection);
			}
		}
	}

	public void tryMove(GoodBeast goodBeast, XY moveDirection) {
		System.out.println("tryMove goodBeast");
		XY newCorrdinates = goodBeast.getXY().add(moveDirection);
		if (!newCorrdinates.equals(goodBeast.getXY())) {
			switch (getEntityType(newCorrdinates)) {
			case Air:
				goodBeast.move(moveDirection);
				break;
			case AutomatedMasterSquirrel:
			case HandOperatedMasterSquirrel:
			case MiniSquirrel:
				getEntity(newCorrdinates).updateEnergy(goodBeast.getEnergy());
				killAndReplace(goodBeast);
				break;
			default:
				return;
			}
		}
	}

	public void tryMove(BadBeast badBeast, XY moveDirection) {
		// Implement bite
		System.out.println("tryMove badBeast");
		XY newCorrdinates = badBeast.getXY().add(moveDirection);
		if (!newCorrdinates.equals(badBeast.getXY())) {
			switch (getEntityType(newCorrdinates)) {
			case Air:
				badBeast.move(moveDirection);
				break;
			case AutomatedMasterSquirrel:
			case HandOperatedMasterSquirrel:
			case MiniSquirrel:
				if (badBeast.bite(getEntity(newCorrdinates))) {
					killAndReplace(badBeast);
				}
				break;
			default:
				return;
			}
		}
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
					// System.out.print("Found Squirrel upright, ");
					return (Squirrel) fb[pos.getX() + k][pos.getY() - i];
				}
				if (right && up && fb[pos.getX() + i][pos.getY() - k] instanceof Squirrel) {
					// System.out.print("Found Squirrel upright, ");
					return (Squirrel) fb[pos.getX() + i][pos.getY() - k];
				}
				// obenlinks
				if (left && up && fb[pos.getX() - i][pos.getY() - k] instanceof Squirrel) {
					// System.out.print("Found Squirrel upleft, ");
					return (Squirrel) fb[pos.getX() - i][pos.getY() - k];
				}
				if (left && up && fb[pos.getX() - k][pos.getY() - i] instanceof Squirrel) {
					// System.out.print("Found Squirrel upleft, ");
					return (Squirrel) fb[pos.getX() - k][pos.getY() - i];
				}
				// untenrechts
				if (right && down && fb[pos.getX() + k][pos.getY() + i] instanceof Squirrel) {
					// System.out.print("Found Squirrel downright, ");
					return (Squirrel) fb[pos.getX() + k][pos.getY() + i];
				}
				if (right && down && fb[pos.getX() + i][pos.getY() + k] instanceof Squirrel) {
					// System.out.print("Found Squirrel downright, ");
					return (Squirrel) fb[pos.getX() + i][pos.getY() + k];
				}
				// untenlinks
				if (left && down && fb[pos.getX() - i][pos.getY() + k] instanceof Squirrel) {
					// System.out.print("Found Squirrel downleft, ");
					return (Squirrel) fb[pos.getX() - i][pos.getY() + k];
				}
				if (left && down && fb[pos.getX() - k][pos.getY() + i] instanceof Squirrel) {
					// System.out.print("Found Squirrel downleft, ");
					return (Squirrel) fb[pos.getX() - k][pos.getY() + i];
				}
			}
		}
		// System.out.print("Found no Squirrel nearby, ");
		return null;
	}

	public void kill(Entity e) {
		System.out.println("kill " + getEntityType(e.getXY()).getChar());
		b.remove(e.getId());
	}

	public void killAndReplace(Entity e) {
		System.out.println("kill and replace " + getEntityType(e.getX(), e.getY()).getChar());
		int rndX;
		int rndY;
		do {
			rndX = ThreadLocalRandom.current().nextInt(1, fb[0].length);
			rndY = ThreadLocalRandom.current().nextInt(1, fb[1].length);
		} while (getEntityType(rndX, rndY) != EntityType.Air || (e.getX() == rndX && e.getY() == rndY));
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
