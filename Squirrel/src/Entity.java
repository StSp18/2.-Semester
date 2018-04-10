
public abstract class Entity {
	private int id, energy;
	private final int startEnergy;
	private xy xy;
	Entity(int id, int energy, int x,int y){
		this.id = id;
		this.energy = energy;
		startEnergy = energy;
		xy = new xy(x, y);	
	}

	public boolean Collision(Entity e) {
		return false;
	}
	
	public void setLocation(int x, int y) {
		xy.setLocation(x, y);
	}

	public void nextStep() {
		xy.rndDirection();
	}
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
