package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

import java.util.logging.Logger;

public abstract class Entity {
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	private int id, energy;
	public XY xy;
	protected Entity(int id, int energy, int x,int y){
		this.id = id;
		this.energy = energy;
		xy = new XY(x, y);	
	}
	
	public void updateEnergy(int dEnergy) {
	    if(this instanceof MasterSquirrel) {
            if(energy + dEnergy < 0) {
                energy = 0;
            } else {
                energy += dEnergy;
            }
        } else {
            energy += dEnergy;
        }
		logger.finest(this.getClass().getName() + ": delta: " + dEnergy + ", New value: " + energy);
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
	
	public boolean equals(Entity e) {
		return getId() == e.getId();
	}
	
	public String toString() {
		return ("Id: " + id + ", Energy: " + energy + ", X: " + getX() + ", Y: " + getY());
	}
}
