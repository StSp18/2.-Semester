
public class Wall extends Entity {

	Wall(int id, int energy, int x, int y) {
		super(id, energy, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void nextStep() {
	}
	
	public String toString() {
		return "Type: Wall, " + super.toString();
	}


}
