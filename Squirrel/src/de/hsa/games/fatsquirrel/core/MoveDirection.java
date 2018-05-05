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
		MoveDirection[] all = values();
		for(int i=0;i<all.length;i++) {
			if(all[i].getMoveDirection().equals(movedirection)) {
				return all[i];
			}
		}
		return stay;
	}
	
	public MoveDirection rndMoveDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
		return values()[randomNum];
		
	}
	
	public XY getMoveDirection() {
		return moveDirection;
	}
}
