package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

import java.util.concurrent.ThreadLocalRandom;

public class FlattenedBoard implements BoardView, EntityContext {
	private Board board;
	private Entity[][] flattenedBoard;
	
	public FlattenedBoard(Entity[][] fb, Board b) {
		this.flattenedBoard = fb;
		this.board = b;
	}

	public EntityType getEntityType(int x, int y) {
		return getEntityType(new XY(x, y));
	}

	public EntityType getEntityType(XY xy) {
		Entity e = flattenedBoard[xy.getX()][xy.getY()];
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

	@Override
	public void createMiniSquirrel(MasterSquirrel master,  XY direction, int energy) throws NotEnoughEnergyException {
		board.add(master.createMiniSquirrel(energy, direction));
	}

	public XY getSize() {
		return board.getSize();
	}

	public void tryMove(MasterSquirrel master, XY moveDirection) {
//		System.out.println("tryMove MasterSquirrel");
		XY newCoordinates = master.getXY().add(moveDirection);
		if (!newCoordinates.equals(master.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case Air:
				break;
			case BadBeast:
				if (((BadBeast) getEntity(newCoordinates)).bite(master)) {
					killAndReplace(getEntity(newCoordinates));
				} else {
					return;
				}
				break;
			case BadPlant:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GoodBeast:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GoodPlant:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case HandOperatedMasterSquirrel:
			case AutomatedMasterSquirrel:
				return;
			case MiniSquirrel:
				if (master.myMiniSquirrel(getEntity(newCoordinates))) {
					master.updateEnergy(getEntity(newCoordinates).getEnergy());
				} else {
					master.updateEnergy(150);
				}
				kill(getEntity(newCoordinates));
				break;
			case Wall:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				master.wallBump();
				return;
			default:
				break;
			}
			master.move(moveDirection);
		}
	}

	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
//		System.out.println("tryMove miniSquirrel");
		XY newCoordinates = miniSquirrel.getXY().add(moveDirection);
		if (!newCoordinates.equals(miniSquirrel.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case Air:
				break;
			case BadBeast:
				if (((BadBeast) getEntity(newCoordinates)).bite(miniSquirrel)) {
					killAndReplace(getEntity(newCoordinates));
				} else {
					return;
				}
				break;
			case BadPlant:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GoodBeast:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GoodPlant:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case AutomatedMasterSquirrel:
			case HandOperatedMasterSquirrel:
				if (miniSquirrel.getMaster() == getEntity(newCoordinates)) {
					getEntity(newCoordinates).updateEnergy(miniSquirrel.getEnergy());
				}
				kill(miniSquirrel);
				return;
			case MiniSquirrel:
				if (miniSquirrel.getMaster() != ((MiniSquirrel) getEntity(newCoordinates)).getMaster()) {
					kill(miniSquirrel);
				}
				return;
			case Wall:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
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
//		System.out.println("tryMove goodBeast");
		XY newCoordinates = goodBeast.getXY().add(moveDirection);
		if (!newCoordinates.equals(goodBeast.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case Air:
				goodBeast.move(moveDirection);
				break;
			case AutomatedMasterSquirrel:
			case HandOperatedMasterSquirrel:
			case MiniSquirrel:
				getEntity(newCoordinates).updateEnergy(goodBeast.getEnergy());
				killAndReplace(goodBeast);
				break;
			default:
				return;
			}
		}
	}

	public void tryMove(BadBeast badBeast, XY moveDirection) {
//		System.out.println("tryMove badBeast");
		XY newCoordinates = badBeast.getXY().add(moveDirection);
		if (!newCoordinates.equals(badBeast.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case Air:
				badBeast.move(moveDirection);
				break;
			case AutomatedMasterSquirrel:
			case HandOperatedMasterSquirrel:
			case MiniSquirrel:
				if (badBeast.bite(getEntity(newCoordinates))) {
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
			if (pos.getX() + i > flattenedBoard[0].length - 1)
				right = false;
			if (pos.getX() - i < 0)
				left = false;
			if (pos.getY() + i > flattenedBoard[1].length - 1)
				down = false;
			if (pos.getY() - i < 0)
				up = false;
			for (int k = 0; k <= i; k++) {
				// obenrechts
				if (right && up && flattenedBoard[pos.getX() + k][pos.getY() - i] instanceof Squirrel) {
					// System.out.print("Found Squirrel upright, ");
					return (Squirrel) flattenedBoard[pos.getX() + k][pos.getY() - i];
				}
				if (right && up && flattenedBoard[pos.getX() + i][pos.getY() - k] instanceof Squirrel) {
					// System.out.print("Found Squirrel upright, ");
					return (Squirrel) flattenedBoard[pos.getX() + i][pos.getY() - k];
				}
				// obenlinks
				if (left && up && flattenedBoard[pos.getX() - i][pos.getY() - k] instanceof Squirrel) {
					// System.out.print("Found Squirrel upleft, ");
					return (Squirrel) flattenedBoard[pos.getX() - i][pos.getY() - k];
				}
				if (left && up && flattenedBoard[pos.getX() - k][pos.getY() - i] instanceof Squirrel) {
					// System.out.print("Found Squirrel upleft, ");
					return (Squirrel) flattenedBoard[pos.getX() - k][pos.getY() - i];
				}
				// untenrechts
				if (right && down && flattenedBoard[pos.getX() + k][pos.getY() + i] instanceof Squirrel) {
					// System.out.print("Found Squirrel downright, ");
					return (Squirrel) flattenedBoard[pos.getX() + k][pos.getY() + i];
				}
				if (right && down && flattenedBoard[pos.getX() + i][pos.getY() + k] instanceof Squirrel) {
					// System.out.print("Found Squirrel downright, ");
					return (Squirrel) flattenedBoard[pos.getX() + i][pos.getY() + k];
				}
				// untenlinks
				if (left && down && flattenedBoard[pos.getX() - i][pos.getY() + k] instanceof Squirrel) {
					// System.out.print("Found Squirrel downleft, ");
					return (Squirrel) flattenedBoard[pos.getX() - i][pos.getY() + k];
				}
				if (left && down && flattenedBoard[pos.getX() - k][pos.getY() + i] instanceof Squirrel) {
					// System.out.print("Found Squirrel downleft, ");
					return (Squirrel) flattenedBoard[pos.getX() - k][pos.getY() + i];
				}
			}
		}
		// System.out.print("Found no Squirrel nearby, ");
		return null;
	}

	public void kill(Entity e) {
//		System.out.println("kill " + getEntityType(e.getXY()).getChar());
		board.remove(e.getId());
	}

	public void killAndReplace(Entity e) {
//		System.out.println("kill and replace " + getEntityType(e.getX(), e.getY()).getChar());
		int rndX;
		int rndY;
		do {
			rndX = ThreadLocalRandom.current().nextInt(1, flattenedBoard[0].length);
			rndY = ThreadLocalRandom.current().nextInt(1, flattenedBoard[1].length);
		} while (getEntityType(rndX, rndY) != EntityType.Air || (e.getX() == rndX && e.getY() == rndY));
		if (e instanceof BadBeast) {
			board.relocate(e, new BadBeast(rndX, rndY));
		} else if (e instanceof GoodBeast) {
			board.relocate(e, new GoodBeast(rndX, rndY));
		} else if (e instanceof GoodPlant) {
			board.relocate(e, new GoodPlant(rndX, rndY));
		} else if (e instanceof BadPlant) {
			board.relocate(e, new BadPlant(rndX, rndY));
		}
	}

	private Entity getEntity(XY coordinates) {
		return flattenedBoard[coordinates.getX()][coordinates.getY()];
	}

	public String toString() {
		String s = "";

		for (int k = 0; k < flattenedBoard[1].length; k++) {
			for (int i = 0; i < flattenedBoard[0].length; i++) {
				if (flattenedBoard[i][k] instanceof BadPlant) {
					s += '-';
				} else if (flattenedBoard[i][k] instanceof GoodPlant) {
					s += '+';
				} else if (flattenedBoard[i][k] instanceof BadBeast) {
					s += 'B';
				} else if (flattenedBoard[i][k] instanceof GoodBeast) {
					s += 'G';
				} else if (flattenedBoard[i][k] instanceof MasterSquirrel) {
					s += 'O';
				} else if (flattenedBoard[i][k] instanceof MiniSquirrel) {
					s += 'o';
				} else if (flattenedBoard[i][k] instanceof Wall) {
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
