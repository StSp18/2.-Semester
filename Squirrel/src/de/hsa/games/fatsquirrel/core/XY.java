package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public final class XY {
	private final int x, y;
	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public XY rndDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
		switch (randomNum) {
		case 1:
			return new XY(x-1, y-1);
		case 2:
			return new XY(x, y-1);
		case 3:
			return new XY(x+1, y-1);
		case 4:
			return new XY(x-1, y);
		case 5:
			return new XY(x+1, y);
		case 6:
			return new XY(x-1, y+1);
		case 7:
			return new XY(x, y+1);
		case 8:
		default:
			return new XY(x+1, y+1);
		}
		
	}
	
	public XY add(XY xy) {
		return new XY(x+xy.getX(),y+xy.getY());
	}
	
	public XY move(XY moveDirection) {
		return new XY(x+moveDirection.getX(),y+moveDirection.getY() );
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return ("X: " + x + ", Y: " + y);
	}
}
