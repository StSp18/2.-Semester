package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class FlattenedBoard implements BoardView, EntityContext {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
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
			return EntityType.BAD_PLANT;
		} else if (e instanceof GoodPlant) {
			return EntityType.GOOD_PLANT;
		} else if (e instanceof BadBeast) {
			return EntityType.BAD_BEAST;
		} else if (e instanceof GoodBeast) {
			return EntityType.GOOD_BEAST;
		} else if (e instanceof MasterSquirrel) {
			return EntityType.MASTER_SQUIRREL;
		} else if (e instanceof MiniSquirrel) {
			return EntityType.MINI_SQUIRREL;
		} else if (e instanceof Wall) {
			return EntityType.WALL;
		} else {
			return EntityType.NONE;
		}
	}

	@Override
	public void createMiniSquirrel(MasterSquirrel master,  XY direction, int energy) throws SpawnException {
		board.add(master.createMiniSquirrel(energy, direction));
	}

    @Override
    public void implodeMiniSquirrel(MiniSquirrel miniSquirrel, int radius) {
	    int impactArea = (int) (radius * radius * 3.14);
	    int accumulatedEnergy = 0;
	    int xBorderMin = 0;
        int xBorderMax = getSize().getX();
	    int yBorderMin = 0;
        int yBorderMax = getSize().getY();
        if(miniSquirrel.getXY().getX()-radius > xBorderMin) {
            xBorderMin = miniSquirrel.getXY().getX()-radius;
        }
        if(miniSquirrel.getXY().getX() + radius < xBorderMax) {
            xBorderMax = miniSquirrel.getXY().getX() + radius;
        }
        if(miniSquirrel.getXY().getY()-radius > yBorderMin) {
            yBorderMin = miniSquirrel.getXY().getY()-radius;
        }
        if(miniSquirrel.getXY().getY() + radius < yBorderMax) {
            yBorderMax = miniSquirrel.getXY().getY() + radius;
        }

        for(int i=xBorderMin; i<xBorderMax; i++) {
            for (int k=yBorderMin; k<yBorderMax;k++) {
                int distance = 0;
                if(miniSquirrel.getX()-i<0) {
                    distance -= miniSquirrel.getX()-i;
                } else {
                    distance += miniSquirrel.getX()-i;
                }
                if(miniSquirrel.getY()-k<0) {
                    distance -= miniSquirrel.getY()-k;
                } else {
                    distance += miniSquirrel.getY()-k;
                }
                int energyLoss = 200 * miniSquirrel.getEnergy()/impactArea * (1 - distance/radius);
                if (energyLoss > 0) {
                    Entity e = getEntity(flattenedBoard[i][k].getXY());
                    switch (getEntityType(flattenedBoard[i][k].getXY())) {
                        case BAD_PLANT:
                        case BAD_BEAST:
                        case GOOD_PLANT:
                        case GOOD_BEAST:
                            accumulatedEnergy += looseEnergy(e, energyLoss);
                            break;
                        case MASTER_SQUIRREL:
                            if(!((MasterSquirrel)e).myMiniSquirrel(miniSquirrel)) {
                                if(e.getEnergy() < energyLoss) {
                                    accumulatedEnergy += e.getEnergy();
                                } else {
                                    accumulatedEnergy += energyLoss;
                                }
                                e.updateEnergy(-energyLoss);
                            }
                            break;
                        case MINI_SQUIRREL:
                            if(!((MiniSquirrel)e).getMaster().myMiniSquirrel(miniSquirrel)) {
                                if (e.getEnergy() < energyLoss) {
                                    accumulatedEnergy += e.getEnergy();
                                    kill(e);
                                } else {
                                    accumulatedEnergy += energyLoss;
                                    e.updateEnergy(-energyLoss);
                                }
                            }
                            break;
                        case WALL:
                            break;
                        case NONE:
                            break;
                    }
                }
            }
        }
        miniSquirrel.getMaster().updateEnergy(accumulatedEnergy);
        kill(miniSquirrel);
    }

    @Override
    public long getRemainingSteps() {
        return board.getRemainingSteps();
    }

    @Override
    public boolean isMine(XY xy, Entity e) {
	    if(e instanceof MasterSquirrel) {
            if(getEntityType(xy) == EntityType.MINI_SQUIRREL) {
                return ((MiniSquirrel) getEntity(xy)).getMaster() == e;
            }
        } else if (e instanceof MiniSquirrel) {
            return ((MiniSquirrel) e).getMaster() == getEntity(xy);
        }
        return false;
    }

    private int looseEnergy(Entity e, int energyLoss) {
	    int accumulatedEnergy;
	    int k = 1;
	    if(e.getEnergy() < 0) {
	        k = -1;
        }
        if(k*e.getEnergy() < energyLoss) {
            accumulatedEnergy = k*e.getEnergy();
            killAndReplace(e);
        } else {
            accumulatedEnergy = energyLoss;
            e.updateEnergy(k*-energyLoss);
        }
        return accumulatedEnergy;
    }

    public XY getSize() {
		return board.getSize();
	}

	public void tryMove(MasterSquirrel master, XY moveDirection) {
	    // TODO logger finest
//		System.out.println("tryMove MASTER_SQUIRREL");
		XY newCoordinates = master.getXY().add(moveDirection);
		if (!newCoordinates.equals(master.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case NONE:
				break;
			case BAD_BEAST:

				if (((BadBeast) getEntity(newCoordinates)).bite(master)) {
					killAndReplace(getEntity(newCoordinates));
				} else {
					return;
				}
				break;
			case BAD_PLANT:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GOOD_BEAST:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GOOD_PLANT:
				master.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case MASTER_SQUIRREL:
				return;
			case MINI_SQUIRREL:
				if (master.myMiniSquirrel(getEntity(newCoordinates))) {
					master.updateEnergy(getEntity(newCoordinates).getEnergy());
				} else {
					master.updateEnergy(150);
				}
				kill(getEntity(newCoordinates));
				break;
			case WALL:
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
        // TODO logger finest
//		System.out.println("tryMove miniSquirrel");
		XY newCoordinates = miniSquirrel.getXY().add(moveDirection);
		if (!newCoordinates.equals(miniSquirrel.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case NONE:
				break;
			case BAD_BEAST:
				if (((BadBeast) getEntity(newCoordinates)).bite(miniSquirrel)) {
					killAndReplace(getEntity(newCoordinates));
				} else {
					return;
				}
				break;
			case BAD_PLANT:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GOOD_BEAST:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case GOOD_PLANT:
				miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
				killAndReplace(getEntity(newCoordinates));
				break;
			case MASTER_SQUIRREL:
				if (miniSquirrel.getMaster() == getEntity(newCoordinates)) {
					getEntity(newCoordinates).updateEnergy(miniSquirrel.getEnergy());
				}
				kill(miniSquirrel);
				return;
			case MINI_SQUIRREL:
				if (miniSquirrel.getMaster() != ((MiniSquirrel) getEntity(newCoordinates)).getMaster()) {
					kill(miniSquirrel);
				}
				return;
			case WALL:
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
        // TODO logger finest
		XY newCoordinates = goodBeast.getXY().add(moveDirection);
		if (!newCoordinates.equals(goodBeast.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case NONE:
				goodBeast.move(moveDirection);
				break;
			case MASTER_SQUIRREL:
			case MINI_SQUIRREL:
				getEntity(newCoordinates).updateEnergy(goodBeast.getEnergy());
				killAndReplace(goodBeast);
				break;
			default:
				return;
			}
		}
	}

	public void tryMove(BadBeast badBeast, XY moveDirection) {
        // TODO logger finest, MINI_SQUIRREL negative energy still allowed
		XY newCoordinates = badBeast.getXY().add(moveDirection);
		if (!newCoordinates.equals(badBeast.getXY())) {
			switch (getEntityType(newCoordinates)) {
			case NONE:
				badBeast.move(moveDirection);
				break;
			case MASTER_SQUIRREL:
			case MINI_SQUIRREL:
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
		board.remove(e.getId());
	}

	public void killAndReplace(Entity e) {
		int rndX;
		int rndY;
		do {
			rndX = ThreadLocalRandom.current().nextInt(1, flattenedBoard[0].length);
			rndY = ThreadLocalRandom.current().nextInt(1, flattenedBoard[1].length);
		} while (getEntityType(rndX, rndY) != EntityType.NONE || (e.getX() == rndX && e.getY() == rndY));
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
