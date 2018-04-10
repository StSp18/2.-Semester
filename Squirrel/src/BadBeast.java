
public class BadBeast extends Entity {

	BadBeast(int id, int energy, int x, int y) {
		super(id, energy, x, y);
	}

	public String toString() {
		return "Type: BadBeast, " + super.toString();
	}
}
