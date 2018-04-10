
public class GoodBeast extends Entity {

	GoodBeast(int id, int x, int y) {
		super(id, 200, x, y);
		// TODO Auto-generated constructor stub
	}

	public void nextStep() {
		xy = xy.rndDirection();
	}
	
	public boolean collision(Entity e) {
		return false;
	}
	
	public String toString() {
		return "Type: GoodBeast, " + super.toString();
	}
}
