package de.hsa.games.fatsquirrel.core;

import java.util.concurrent.ThreadLocalRandom;

public final class XY {
	private final int x, y;
	public XY(int x, int y) {
		this.x = x;
		this.y = y;
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
