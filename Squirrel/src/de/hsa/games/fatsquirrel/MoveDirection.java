package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.core.XY;

public enum MoveDirection {
	up(new XY(0, -1)), down(new XY(0, 1)), right(new XY(1, 0)), left(new XY(-1, 1)), upright(
			new XY(1, -1)), upleft(new XY(-1, -1)), downright(new XY(1, 1)), downleft(new XY(-1, 1)), stay(new XY(0, 0));

	private final XY moveDirection;

	private MoveDirection(XY moveDirection) {
		this.moveDirection = moveDirection;
	}

	public MoveDirection searchMoveDirection (XY movedirection) {
		if(up.getMoveDirection() == movedirection)
			return up;
		if(down.getMoveDirection() == movedirection)
			return down;
		if(right.getMoveDirection() == movedirection)
			return right;
		if(left.getMoveDirection() == movedirection)
			return left;
		if(upright.getMoveDirection() == movedirection)
			return upright;
		if(upleft.getMoveDirection() == movedirection)
			return upleft;
		if(upleft.getMoveDirection() == movedirection)
			return downright;
		if(upleft.getMoveDirection() == movedirection)
			return downleft;
		return stay;
	}
	
	public XY getMoveDirection() {
		return moveDirection;
	}
}
