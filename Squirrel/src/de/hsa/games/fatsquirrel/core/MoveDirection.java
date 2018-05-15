package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public enum MoveDirection {
	up(new XY(0, -1)), down(new XY(0, 1)), right(new XY(1, 0)), left(new XY(-1, 0)), upright(
			new XY(1, -1)), upleft(new XY(-1, -1)), downright(new XY(1, 1)), downleft(new XY(-1, 1)), stay(new XY(0, 0));


	private final XY moveDirection;

	private MoveDirection(XY moveDirection) {
		this.moveDirection = moveDirection;
	}

	public static MoveDirection moveAway(XY e, XY s) {
		return moveTowards(s, e);
	}

	public static MoveDirection moveTowards(XY e, XY s) {
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
		return searchMoveDirection(new XY(x, y));

	}
	
	public static MoveDirection searchMoveDirection (XY movedirection) {
		MoveDirection[] all = values();
		for(int i=0;i<all.length;i++) {
			if(all[i].getXY().equals(movedirection)) {
				return all[i];
			}
		}
		return stay;
	}
	
	public static MoveDirection rndMoveDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
		return values()[randomNum];
		
	}
	
	public XY getXY() {
		return moveDirection;
	}
}
