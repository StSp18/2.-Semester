package de.hsa.games.fatsquirrel.core;

public abstract class Entity {
	private int id, energy;
	public XY xy;
	protected Entity(int id, int energy, int x,int y){
		this.id = id;
		this.energy = energy;
		xy = new XY(x, y);	
	}
	
	public void updateEnergy(int dEnergy) {
		energy += dEnergy;
	}
	
	public XY getXY() {
		return xy;
	}

	public int getId() {
		return id;
	}
	public int getEnergy() {
		return energy;
	}
	public int getX() {
		return xy.getX();
	}
	public int getY() {
		return xy.getY();
	}
	
	public String toString() {
		return ("Id: " + id + ", Energy: " + energy + ", X: " + getX() + ", Y: " + getY());
	}
}
