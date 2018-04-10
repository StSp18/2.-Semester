
public abstract class Entity {
	private int id, energy;
	private final int startEnergy;
	public XY xy;
	Entity(int id, int energy, int x,int y){
		this.id = id;
		this.energy = energy;
		startEnergy = energy;
		xy = new XY(x, y);	
	}

	public abstract boolean collision(Entity e);

	public abstract void nextStep();
	
	public void updateEnergy(int dEnergy) {
		energy += dEnergy;
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
		return ("Id: " + id + ", Start Energy: " + startEnergy + ", Energy: " + energy + ", X: " + getX() + ", Y: " + getY());
	}
}
