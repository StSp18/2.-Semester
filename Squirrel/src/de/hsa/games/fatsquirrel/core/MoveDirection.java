package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public enum MoveDirection {
	up(new XY(0, -1)), down(new XY(0, 1)), right(new XY(1, 0)), left(new XY(-1, 0)), upright(
			new XY(1, -1)), upleft(new XY(-1, -1)), downright(new XY(1, 1)), downleft(new XY(-1, 1)), stay(new XY(0, 0));

	private final XY moveDirection;

	private MoveDirection(XY moveDirection) {
		this.moveDirection = moveDirection;
	}

	public MoveDirection searchMoveDirection (XY movedirection) {
		if(up.getMoveDirection().getX() == movedirection.getX() && up.getMoveDirection().getY() == movedirection.getY())
			return up;
		if(down.getMoveDirection().getX() == movedirection.getX() && down.getMoveDirection().getY() == movedirection.getY())
			return down;
		if(right.getMoveDirection().getX() == movedirection.getX() && right.getMoveDirection().getY() == movedirection.getY())
			return right;
		if(left.getMoveDirection().getX() == movedirection.getX() && left.getMoveDirection().getY() == movedirection.getY())
			return left;
		if(upright.getMoveDirection().getX() == movedirection.getX() && upright.getMoveDirection().getY() == movedirection.getY())
			return upright;
		if(upleft.getMoveDirection().getX() == movedirection.getX() && upleft.getMoveDirection().getY() == movedirection.getY())
			return upleft;
		if(downright.getMoveDirection().getX() == movedirection.getX() && downright.getMoveDirection().getY() == movedirection.getY())
			return downright;
		if(downleft.getMoveDirection().getX() == movedirection.getX() && downleft.getMoveDirection().getY() == movedirection.getY())
			return downleft;
		return stay;
	}
	
	public MoveDirection rndMoveDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
		switch (randomNum) {
		case 1:
			return up;
		case 2:
			return down;
		case 3:
			return left;
		case 4:
			return right;
		case 5:
			return upleft;
		case 6:
			return upright;
		case 7:
			return downleft;
		default:
			return downright;
		}
		
	}
	
	public XY getMoveDirection() {
		return moveDirection;
	}
}
