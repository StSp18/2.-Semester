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
			if(fb[x][y].collision(miniSquirrel)) {
				if(miniSquirrel.getEnergy()<0) {
					kill(miniSquirrel);
				}
				miniSquirrel.setMoveDirection(new XY(0,0));
				return;
			}
		}
		miniSquirrel.setMoveDirection(moveDirection);
		
	}

	public void tryMove(GoodBeast goodBeast, XY moveDirection) {
		int x = goodBeast.getX() + moveDirection.getX();
		int y = goodBeast.getX() + moveDirection.getX();
		if (getEntityType(x, y) != EntityType.Air) {
			if(fb[x][y].collision(goodBeast)) {
				if(goodBeast.alive()) {
					goodBeast.setMoveDirection(new XY(0,0));
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
			if(fb[x][y].collision(badBeast)) {
				if(badBeast.alive()) {
					badBeast.setMoveDirection(new XY(0,0));
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
			if(fb[x][y].collision(master)) {
				master.setMoveDirection(new XY(0,0));
				return;
			}
		}
		master.setMoveDirection(moveDirection);
	}

	public Squirrel nearestPlayerEntity(XY pos) {
//		int i;
//		if(pos.getX()-6>0)
//			i = pos.getX()-6;
//		else
//			i = 1;
//		int maxi;
//		if(pos.getX()+6<fb[0].length-1)
//			maxi = pos.getX()+6;
//		else
//			maxi = fb[0].length-2;
//		int k;
//		if(pos.getY()-6>0)
//			k = pos.getY()-6;
//		else
//			k = 1;
//		int maxk;
//		if(pos.getX()+6<fb[1].length-1)
//			maxk = pos.getX()+6;
//		else
//			maxk = fb[1].length-2;
//
//		for(;i < maxi; i++) {
//			for(; k < maxk; k++) {
//				if(fb[i][k] instanceof Squirrel) {
//					return (Squirrel) fb[i][k];
//				}
//			}
//		}
//		return null;
		for(int i=0; i<=6;i++) {
			
		}
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
		if(e instanceof BadBeast) {
			
		} else if(e instanceof GoodBeast) {
			
		} else if(e instanceof GoodPlant) {
			
		} else if(e instanceof BadPlant) {
			
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
