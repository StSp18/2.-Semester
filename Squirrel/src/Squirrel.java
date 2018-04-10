
public abstract class Squirrel extends Entity {

	Squirrel(int id, int energy, int x, int y) {
		super(id, energy, x, y);
	}
	
	
	
	public boolean collision(Entity e) {
		return false;
	}
	
	public String toString() {
		return super.toString();
	}
}
