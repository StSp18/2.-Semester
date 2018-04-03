
public class BadBeast extends Entity {

	BadBeast(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		// TODO Auto-generated constructor stub
	}

	public void nextStep() {
		rndDirection();
	}

	public String toString() {
		return super.toString();
	}
}
